#!/bin/bash

mkdir third_party
pushd third_party

wget https://github.com/raysan5/raylib/archive/refs/tags/4.0.0.tar.gz -O raylib.tar.gz
patool extract raylib.tar.gz
rm -rf raylib.tar.gz
pushd raylib-4.0.0/src


make -j $($(grep -c ^processor /proc/cpuinfo) - 1) PLATFORM=PLATFORM_DESKTOP RAYLIB_LIBTYPE=SHARED

popd
popd






