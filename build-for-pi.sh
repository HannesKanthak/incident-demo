#!/bin/bash

set -e

IMAGE_NAME="demo-app"
TAG="arm64"
EXPORT_FILE="demo-app-${TAG}.tar.gz"

echo "🚀 Initialisiere buildx (falls nötig)..."
docker buildx create --use --name tempbuilder 2>/dev/null || true
docker buildx inspect

echo "🔨 Baue Docker-Image für Raspberry Pi (ARM64)..."
docker buildx build \
  --platform linux/arm64 \
  --tag ${IMAGE_NAME}:${TAG} \
  --output type=docker \
  .

echo "📦 Exportiere Image nach ${EXPORT_FILE}..."
docker save ${IMAGE_NAME}:${TAG} | gzip > ${EXPORT_FILE}

echo "✅ Fertig!"
echo "👉 Übertrage das Image z.B. mit:"
echo "   scp ${EXPORT_FILE} pi@raspberrypi.local:~/"
