#!/usr/bin/env python3
"""Source consistency checks only. Does not compile Kotlin or certify Android compatibility."""
import re
import struct
import xml.etree.ElementTree as ET
from pathlib import Path

root = Path(__file__).resolve().parents[1]
android = "{http://schemas.android.com/apk/res/android}"
manifest = ET.parse(root / "app/src/main/AndroidManifest.xml").getroot()
assert not manifest.findall("uses-permission"), "Unexpected privileged/network permission"
features = {x.attrib[android + "name"]: x.attrib[android + "required"] for x in manifest.findall("uses-feature")}
assert features["android.hardware.touchscreen"] == "false"
assert features["android.software.leanback"] == "true"
categories = {x.attrib[android + "name"] for x in manifest.iter("category")}
assert "android.intent.category.HOME" in categories
assert "android.intent.category.LEANBACK_LAUNCHER" in categories
resources = root / "feature/home/src/main/res"
base = {x.attrib["name"] for x in ET.parse(resources / "values/strings.xml").getroot()}
fr = {x.attrib["name"] for x in ET.parse(resources / "values-fr/strings.xml").getroot()}
assert base == fr, "Incomplete translation"
used = set()
for file in (root / "feature/home/src/main/kotlin").rglob("*.kt"):
    used.update(re.findall(r"R\.string\.(\w+)", file.read_text()))
assert used <= base, f"Missing resources: {used - base}"
for file in root.rglob("*.xml"):
    if "build" not in file.parts: ET.parse(file)
for file in root.rglob("*.kt"):
    assert "Runtime.getRuntime().exec" not in file.read_text(), "Shell execution in runtime"
    assert "Build.MANUFACTURER" not in file.read_text(), "Manufacturer-coupled implementation"
theme = (root / "feature/home/src/main/kotlin/fr/liquide/launcher/home/LiquideTheme.kt").read_text()
design = (root / "DESIGN.md").read_text()
for value in re.findall(r"0xFF([A-F0-9]{6})", theme):
    assert "#" + value in design, f"Undocumented token {value}"
png = root / "app/src/main/res/drawable-xhdpi/tv_banner.png"
assert struct.unpack(">II", png.read_bytes()[16:24]) == (320, 180)
print("PASS: manifests, translations, XML, token mapping, banner, no shell/OEM coupling")
