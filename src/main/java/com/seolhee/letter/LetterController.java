package com.seolhee.letter;

import java.util.List;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.seolhee.book.chap11.Member;

@Controller
public class LetterController {

	@Autowired
	LetterDao letterDao;
	static final Logger logger = LogManager.getLogger();
	
	//편지 보기
	@GetMapping("/letter/view")
	public void view(@RequestParam("letterId") String letterId,
			@SessionAttribute("MEMBER") Member member, Model model) {
		Letter letter = letterDao.getLetter(letterId, member.getMemberId());
		model.addAttribute("letter", letter);
}
	//보낸 목록
	@GetMapping("/letter/sendList")
	public void listSent(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@SessionAttribute("MEMBER") Member member, Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int ROWS_PER_PAGE = 100;
		int offset = (page - 1) * ROWS_PER_PAGE;

		List<Letter> sendLetters = letterDao.sendLetters(member.getMemberId(),
				offset, ROWS_PER_PAGE);
		int count = letterDao.countLettersSent(member.getMemberId());

		model.addAttribute("sendLetters", sendLetters);
		model.addAttribute("count", count);
	}
	//받은 목록
	@GetMapping("/letter/receiveList")
	public void receiveLetters(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@SessionAttribute("MEMBER") Member member,Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int ROW_PER_PAGE = 100;
		int offset = (page - 1) * ROW_PER_PAGE;

		List<Letter> receiveLetters = letterDao.receiveLetters(member.getMemberId(),
				offset, ROW_PER_PAGE);
		int count = letterDao.countLettersReceived(member.getMemberId());

		model.addAttribute("receiveLetters", receiveLetters);
		model.addAttribute("count", count);
	}
	//편지 저장
	@PostMapping("/letter/add")
	public String add(Letter letter,
			@SessionAttribute("MEMBER") Member member) {
		letter.setSenderId(member.getMemberId());
		letter.setSenderName(member.getName());
		letterDao.addLetter(letter);
		return "redirect:/app/letter/sendList";
	}
	//편지 삭제
	@GetMapping("/letter/delete")
	public String delete(
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam("letterId") String letterId,
			@SessionAttribute("MEMBER") Member member) {
		int updatedRows = letterDao.deleteLetter(letterId,
				member.getMemberId());
		if (updatedRows == 0)
			// 자신의 편지가 아닐 경우 삭제되지 않음
			throw new RuntimeException("No Authority!");

		if ("SENT".equals(mode))
			return "redirect:/app/letter/sendList";
		else
			return "redirect:/app/letter/receiveList";
}
	
}
