package com.seolhee.letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.seolhee.article.Article;

public class LetterDao {
	
	static final String ADD_LETTER = "insert letter(title, content, senderId, senderName, receiverId, receiverName) values(?,?,?,?,?,?)";
	static final String SEND_LETTER = "select letterId, title, receiverId, receiverName, cdate from letter where senderId=?";
	static final String RECEIVE_LETTER = "select letterId, title, senderId, senderName, cdate from letter where receiverId=?";
	static final String GET_LETTER = "select letterId, title, content, senderId, senderName, receiverId, receiverName, cdate from letter where letterId=? and (senderId=? or receiverId=?)";
	static final String DELETE_LETTER = "delete from letter where letterId=? and (senderId=? or receiverId=?)";
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	RowMapper<Letter> letterRowMapper = new BeanPropertyRowMapper<>(
			Letter.class);

	public int addLetter(Letter letter) {
		return jdbcTemplate.update(ADD_LETTER, letter.getTitle(),
				letter.getContent(), letter.getSenderId(), letter.getSenderName(), letter.getReceiverId(), letter.getReceiverName());
	}
	
	public Letter sendLetter(String senderId) {
		return jdbcTemplate.queryForObject(SEND_LETTER, letterRowMapper, senderId);
	}
	
	public Letter receiveLetter(String receiverId) {
		return jdbcTemplate.queryForObject(RECEIVE_LETTER, letterRowMapper, receiverId);
	}
	
	public Letter getLetter(String letterId, String senderId, String receiverId) {
		return jdbcTemplate.queryForObject(GET_LETTER,letterRowMapper, letterId, senderId, receiverId);
	}
	
	public int deleteLetter(String letterId, String senderId, String receiverId) {
		return jdbcTemplate.update(DELETE_LETTER, letterId, senderId, receiverId);
	}
	
}
