package com.seolhee.letter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.seolhee.article.Article;
import com.seolhee.book.chap11.Member;


public class LetterController {

	@Autowired
	LetterDao letterDao;
	static final Logger logger = LogManager.getLogger();
	
	@PostMapping("/letter/add")
	public String letterAdd(Letter letter,
			@SessionAttribute("MEMBER") Member member) {
		letter.setSenderId(member.getMemberId());
		letter.setSenderName(member.getName());
		letterDao.addLetter(letter);
		return "redirect:/app/article/list";
	}
}
