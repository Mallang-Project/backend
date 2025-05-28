package backend.spring.service;

import java.time.LocalDate;
import java.util.List;

import backend.spring.dto.response.CountVisitorResponseDto;
import org.springframework.stereotype.Service;

import backend.spring.entity.Visitor;
import backend.spring.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final VisitorRepository visitorRepository;

	public CountVisitorResponseDto getVisitorsCount(){

		List<Visitor> visitorList = visitorRepository.findAll();
		int totalCount = visitorList.size();

		LocalDate today = LocalDate.now();
		int todayCount = (int) visitorList.stream()
			.filter(visitor -> visitor.getCreatedAt().toLocalDate().equals(today))
			.count();

		return new CountVisitorResponseDto(totalCount, todayCount);

	}
}
