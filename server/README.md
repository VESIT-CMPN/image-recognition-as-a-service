# Create container
docker build -t samplehello .
# Remove image
docker rmi imageid --force
# Run container
docker run -p 5000:5000 samplehello
# Start container
docker start samplehello
# Stop Container
docker stop samplehello