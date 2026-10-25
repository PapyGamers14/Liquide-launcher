#!/bin/sh
# Source-only bootstrap: official Gradle distribution + SHA-256, cached outside source archives.
# Replaces the absent binary wrapper in this source delivery. Requires curl, unzip and a JDK 17+.
set -eu
ROOT=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
VERSION=9.6.0
TOOLS="$ROOT/.tooling"
GRADLE="$TOOLS/gradle-$VERSION/bin/gradle"
if [ ! -x "$GRADLE" ]; then
    mkdir -p "$TOOLS"
    TEMP=$(mktemp -d "$TOOLS/download.XXXXXX")
    trap 'rm -rf "$TEMP"' EXIT HUP INT TERM
    curl --fail --location --retry 2 --connect-timeout 15 --max-time 600 \
        "https://services.gradle.org/distributions/gradle-$VERSION-bin.zip" -o "$TEMP/gradle.zip"
    curl --fail --location --retry 2 --connect-timeout 15 --max-time 60 \
        "https://services.gradle.org/distributions/gradle-$VERSION-bin.zip.sha256" -o "$TEMP/checksum"
    EXPECTED=$(tr -d '[:space:]' < "$TEMP/checksum")
    ACTUAL=$(shasum -a 256 "$TEMP/gradle.zip" | cut -d ' ' -f 1)
    [ "$EXPECTED" = "$ACTUAL" ] || { echo 'Gradle checksum mismatch' >&2; exit 1; }
    unzip -q "$TEMP/gradle.zip" -d "$TEMP"
    mv "$TEMP/gradle-$VERSION" "$TOOLS/"
fi
exec "$GRADLE" -p "$ROOT" "$@"
