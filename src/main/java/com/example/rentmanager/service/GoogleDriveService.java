package com.example.rentmanager.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;

@Service
public class GoogleDriveService {

	private static final String APPLICATION_NAME = "Drive API Java";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final List<String> SCOPES = List.of(DriveScopes.DRIVE_FILE);
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	private Drive driveService;

	// Inject đường dẫn file credentials.json từ application.properties
	@Value("${google.drive.credentials.path}")
	private String credentialsFilePath;

	@PostConstruct
	public void init() throws Exception {
		// Load credentials file từ filesystem (hoặc resources nếu cần)
		Resource resource = new FileSystemResource(credentialsFilePath);
		if (!resource.exists()) {
			throw new FileNotFoundException("Credentials file not found at: " + credentialsFilePath);
		}

		InputStream in = resource.getInputStream();

		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Thiết lập flow với OAuth2
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				GoogleNetHttpTransport.newTrustedTransport(),
				JSON_FACTORY,
				clientSecrets,
				SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline")
				.build();

		// Tạo local server receiver cho OAuth2 (localhost:8080)
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

		// Khởi tạo Drive service với credential
		driveService = new Drive.Builder(
				GoogleNetHttpTransport.newTrustedTransport(),
				JSON_FACTORY,
				credential)
				.setApplicationName(APPLICATION_NAME)
				.build();
	}

	// Hàm upload file lên Google Drive
	public String uploadFile(java.io.File localFile) throws Exception {
		// Metadata cho file
		File fileMetadata = new File();
		fileMetadata.setName(localFile.getName());
		fileMetadata.setParents(List.of("1y4LxH1-JEvZ9szaZ9Uj4aWHHv4rSgc3r"));
		// Nội dung file
		InputStreamContent mediaContent = new InputStreamContent(
				Files.probeContentType(localFile.toPath()),  // Tự detect mime-type
				new FileInputStream(localFile));

		// Upload file
		File uploadedFile = driveService.files()
				.create(fileMetadata, mediaContent)
				.setFields("id")
				.execute();

		Permission permission = new Permission();
		permission.setType("anyone");
		permission.setRole("reader");
		driveService.permissions().create(uploadedFile.getId(), permission).execute();

		return uploadedFile.getId(); // trả về ID file Google Drive
	}
}
