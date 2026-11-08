#!/usr/bin/env bash
set -euo pipefail

project_dir="$(cd "$(dirname "$0")" && pwd)"
main_output_dir="$project_dir/out/classes"
test_output_dir="$project_dir/out/test-classes"

"$project_dir/build.sh"
mkdir -p "$test_output_dir"
find "$project_dir/src/test/java" -name '*.java' -exec javac -cp "$main_output_dir" -d "$test_output_dir" {} +
java -cp "$main_output_dir:$test_output_dir" com.littlepay.trips.FareCalculatorTest
