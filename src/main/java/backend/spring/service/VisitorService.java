package backend.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import backend.spring.dto.request.VisitorRequestDto;
import backend.spring.dto.response.VisitorResponseDto;
import backend.spring.entity.Visitor;
import backend.spring.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitorService {

	private final VisitorRepository visitorRepository;

	@Value("${admin.code}")
	private String adminCode;

	public VisitorResponseDto createVisitor(VisitorRequestDto requestDto) {
		String nickname = requestDto.nickname();
		boolean isAdminViewable = adminCode.equals(nickname);

		Visitor visitor = new Visitor(nickname);
		visitorRepository.save(visitor);

		return new VisitorResponseDto(visitor.getId(), nickname, isAdminViewable);
	}
}
