*** 
Schemat bazy danych
***
wallets:
- id (UUID) PK

bank_stocks:
- id (Long) PK
- name (String U)
- quantity (Integer)

wallet_stocks:
- id (Long) PK
- wallet_id (UUID) FK -> wallets
- name (String)
- quantity (Integer)

logs:
- id (Long) PK
- type (String)
- wallet_id (UUID)
- stock_name (String)
- created_at (Timestamp)

