#!/usr/bin/env bash

jupyter nbconvert README.ipynb  --to markdown --output README.md
sed -i '' 's/\x1b\[[0-9;]*m//g' README.md
