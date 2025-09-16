#!/usr/bin/env bash
set -eu

# Check if redocly CLI is installed
if ! command -v redocly >/dev/null 2>&1; then
  echo "Error: redocly CLI not found. Please install it with: npm install @redocly/cli -g"
  exit 1
fi

echo
echo "Bundling OpenAPI"
echo
for dir in ts-*; do
    if [[ -d $dir ]]; then
        resource_dir="${dir}/src/main/resources"
        if [[ -f "${resource_dir}/openapi.yaml" ]]; then
            echo "bundle ${dir}"
            # bundle openapi.yaml to $dir/target/openapi.yaml
            mkdir -p "${dir}/target"
            redocly bundle -o "${dir}/target/openapi.yaml" "${resource_dir}/openapi.yaml"
        fi
    fi
done
