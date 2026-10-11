# Deployment

The final deployment uses Docker Compose with `app`, `db`, and an internal-only `pathfinder` service. Only port 8080 is published. `pgdata` and uploaded assets use named volumes. Keep real `.env` files out of Git; commit `.env.example` only after the configuration contract is implemented. The documented install path will be completed with the walking skeleton.
