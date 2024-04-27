# UserStorage

This RESTful API allows users to search for and download files stored in their personal S3 bucket. Users are identified by their username, and each user has their own folder within the S3 bucket. The API provides functionality for searching files by filename and downloading them.

# Endpoints

Search Files
Method: GET
URL: /search
Parameters:
userName (string, required): The username of the user whose files are to be searched.
searchTerm (string, required): The term to search for in the filenames.
Description: Searches for files within the specified user's folder in the S3 bucket using the given search term.
Response:
200 OK: Returns a list of filenames matching the search term.
404 Not Found: If no files match the search term.
500 Internal Server Error: If an error occurs during the search process.
Download File
Method: GET
URL: /download/{userName}/{fileName}
Parameters:
userName (string, required): The username of the user who owns the file.
fileName (string, required): The name of the file to download.
Description: Downloads the specified file from the user's folder in the S3 bucket.
Response:
200 OK: Returns the file for download.
404 Not Found: If the specified file does not exist.
500 Internal Server Error: If an error occurs during the download process.

# Additional Notes
No Authentication: This API does not require any authentication for simplicity.
S3 Integration: Files are stored in a single S3 bucket, with each user having their own folder.
Search on Filename Only: Searches are performed based on filenames only.
Optimized API Calls: API calls are designed to be efficient and optimized for performance.
Design for Extensibility: The API is designed to be easily extendable for future enhancements or additional features.

# Setup Instructions
Clone this repository.
Install the required dependencies.
Configure AWS credentials and S3 bucket settings.
Deploy the API to your desired hosting platform.
Usage
Send a GET request to /search endpoint with userName and searchTerm parameters to search for files.
Send a GET request to /download/{userName}/{fileName} endpoint to download the desired file.

#Example
Search for files containing "logistics" in the username "sandy":
GET /search?userName=sandy&searchTerm=logistics
Download a file named "example.txt" owned by user "sandy":
GET /download/sandy/example.txt
