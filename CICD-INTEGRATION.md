# CCGC Sustainability Analyzer – CI/CD Integration Guide

Easily integrate the CCGC Sustainability Analyzer into your CI/CD pipeline to measure the environmental impact of your code.

---

## Step 1: Get an API Token (Auth0)

1. Go to the Auth0 dashboard (provided by the system administrator).
2. Navigate to **Applications > APIs** and find the `ccgc-api`.
3. Use the following POST request to obtain an access token using **Client Credentials Flow**:

```
POST https://<your-auth0-domain>/oauth/token
```

**Body:**
```json
{
  "client_id": "YOUR_CLIENT_ID",
  "client_secret": "YOUR_CLIENT_SECRET",
  "audience": "https://ccgc-api",
  "grant_type": "client_credentials"
}
```

**Response:**
```json
{
  "access_token": "YOUR_TOKEN",
  "token_type": "Bearer"
}
```

> Store the `access_token` securely in your CI/CD secrets (e.g., `CCGC_API_TOKEN`).

---

## Step 2: Submit Code for Profiling

Send a POST request to the `/api/analyze` endpoint with your code and metadata:

```
POST https://<your-api-url>/api/analyze
```

**Example `curl` usage:**
```bash
curl -X POST https://<your-api-url>/api/analyze \
  -H "Authorization: Bearer $CCGC_API_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "language": "python",
    "fileNameHint": "example.py",
    "code": "print(\"Hello World\")"
  }'
```

---

## Example GitHub Action Step

```yaml
- name: Run Sustainability Check
  run: |
    curl -X POST https://<your-api-url>/api/analyze \
      -H "Authorization: Bearer ${{ secrets.CCGC_API_TOKEN }}" \
      -H "Content-Type: application/json" \
      -d @submission.json
```

**submission.json**
```json
{
  "language": "js",
  "fileNameHint": "example.js",
  "code": "console.log('Hello');"
}
```

---

## Supported Languages

- `python`
- `js` (JavaScript)
- `java`

---

## Sample API Response

```json
{
  "cpuTimeMs": 98,
  "memoryUsedMb": 7.2,
  "estimatedEnergyJoules": 0.00102,
  "greenScore": 89,
  "carbonIntensity": 56,
  "suggestions": [
    "Consider reducing nested loops",
    "Avoid frequent use of synchronous operations"
  ]
}
```

---
