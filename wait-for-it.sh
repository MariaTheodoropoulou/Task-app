#!/usr/bin/env bash

if [ "$#" -lt 2 ]; then
    echo "Usage: $0 host port [-- command args...]"
    exit 1
fi

HOST="$1"
PORT="$2"
shift 2

TIMEOUT=30
echo "Waiting for $HOST:$PORT to become available (timeout: ${TIMEOUT}s)..."

for ((i=1;i<=TIMEOUT;i++)); do
    nc -z "$HOST" "$PORT" >/dev/null 2>&1 && break
    echo "Still waiting... ($i/$TIMEOUT)"
    sleep 1
done

if ! nc -z "$HOST" "$PORT" >/dev/null 2>&1; then
    echo "Timeout after waiting ${TIMEOUT} seconds for $HOST:$PORT"
    exit 1
fi

echo "$HOST:$PORT is available."

if [ "$#" -gt 0 ]; then
    exec "$@"
fi
