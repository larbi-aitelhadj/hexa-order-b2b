# Use Node.js 20 as base image
FROM node:20

# Set working directory
WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm ci --legacy-peer-deps || npm install --legacy-peer-deps

# Copy application source code
COPY . .

# Start the development server
CMD ["npm", "start", "--", "--host", "0.0.0.0"]

# Expose port 4200
EXPOSE 4200
