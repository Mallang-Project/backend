package backend.spring.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import backend.spring.dto.response.HtmlSaveResponse;
import backend.spring.exception.CustomException;
import backend.spring.exception.ResponseCode;

@Service
public class HtmlShareService {

	private final String baseDir = "/home/ubuntu/mallang-static/result/";
	private final String baseUrl = "https://mallang.info/result/";
	private final String HTML_EXTENSION = ".html";

	public HtmlSaveResponse saveHtml(String html) {
		if (!StringUtils.hasText(html)) {
			throw new CustomException(ResponseCode.INVALID_HTML_FORMAT);
		}

		try {
			Path directory = Paths.get(baseDir);
			if (!Files.exists(directory)) {
				Files.createDirectories(directory);
			}

			String filename = UUID.randomUUID() + HTML_EXTENSION;
			Path filePath = directory.resolve(filename);
			Files.write(filePath, html.getBytes(StandardCharsets.UTF_8));

			return new HtmlSaveResponse(baseUrl + filename);

		} catch (Exception e) {
			throw new CustomException(ResponseCode.FILE_SAVE_ERROR);
		}
	}
}
