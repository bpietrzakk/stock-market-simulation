# Stock Market Simulator

A simplified stock market REST API built as a recruitment task. The service simulates buying and selling stocks between wallets and a central bank, with full audit logging and high availability support.

## Tech Stack

- **Java 21** + **Spring Boot 3.4.x**
- **PostgreSQL** — persistent storage via Spring Data JPA (Hibernate)
- **Maven** — build tool
- **Docker + Docker Compose** — containerized deployment
- **nginx** — reverse proxy / load balancer for high availability
## Architecture

The application runs as multiple instances behind an nginx load balancer. If one instance is killed (e.g. via the `/chaos` endpoint), the remaining instance(s) continue serving requests — ensuring high availability.

```
                 ┌──────────────┐
   request ────▶ │    nginx     │
                 │ (port XXXX)  │
                 └──┬───────┬───┘
                    │       │
              ┌─────▼──┐ ┌──▼─────┐
              │ App #1 │ │ App #2 │
              └─────┬──┘ └──┬─────┘
                    │       │
                 ┌──▼───────▼──┐
                 │  PostgreSQL  │
                 └─────────────┘
```

## How to Run

> **Prerequisites:** Docker and Docker Compose must be installed.

```bash
# TODO: will be updated once Docker setup is complete
./run.sh <PORT>
```

The application will be available at `http://localhost:<PORT>`.

## API Endpoints

<!-- TODO: endpoint documentation will be added as they are implemented -->

## Database Schema

| Table | Columns | Description |
|-------|---------|-------------|
| `wallets` | `id` (UUID, PK) | User wallets |
| `bank_stocks` | `id` (Long, PK), `name` (String, unique), `quantity` (Integer) | Stocks available in the bank |
| `wallet_stocks` | `id` (Long, PK), `wallet_id` (UUID, FK), `name` (String), `quantity` (Integer) | Stocks owned by wallets |
| `logs` | `id` (Long, PK), `type` (String), `wallet_id` (UUID), `stock_name` (String), `created_at` (Timestamp) | Audit log of wallet operations |

## Project Structure

```
src/main/java/com/bpietrzak/stockmarket/
├── controller/       # REST controllers
├── service/          # Business logic
├── repository/       # Spring Data JPA repositories
├── model/            # JPA entities
├── dto/              # Request/response objects
└── exception/        # Custom exceptions + global error handler
```

## License

This project was created as a recruitment task.