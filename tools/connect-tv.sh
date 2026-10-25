#!/bin/sh
# Connection only. Does not change settings, disable packages or select a launcher.
set -eu
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 IP:PORT (use the connection port shown by the TV)" >&2
    exit 2
fi
command -v adb >/dev/null || { echo 'ADB is missing from PATH' >&2; exit 1; }
adb connect "$1"
adb devices -l
