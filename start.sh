#!/bin/sh
# Stockmarket startup script for Linux/macOS
# Usage: ./start.sh [PORT]   (default: 8080)

set -e

PORT="${1:-8080}"

echo "Starting stockmarket on port ${PORT}..."
PORT="${PORT}" docker compose up --build