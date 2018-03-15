# Create container
docker build -t samplehello .
# Remove container
docker rmi containerid --force
# Run container
docker run samplehello
# Start container
docker start samplehello
# Stop Container
docker stop samplehello