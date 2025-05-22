# Parent Image
FROM node:21 AS frontend-builder

# Create working directory
WORKDIR /app

# Copy required libries
COPY package*.json ./


# Run required libries
RUN npm install

# Copy app files 
COPY . .

# Runner image
FROM node:21-slim

# create workdir
WORKDIR /app

# Copy built assets and dependencies from frontend-builder stage
COPY --from=frontend-builder /app .


# Expose port 5173 for the Node.js application
EXPOSE 5173

# Define the default command to run the application in development mode
CMD ["npm", "run", "dev", "--", "--host"]


