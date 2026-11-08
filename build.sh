#!/usr/bin/env bash
set -euo pipefail

project_dir="$(cd "$(dirname "$0")" && pwd)"
output_dir="$project_dir/out/classes"

mkdir -p "$output_dir"
find "$project_dir/src/main/java" -name '*.java' -exec javac -d "$output_dir" {} +
