package com.poscoict.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.util.WebUtil;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String index(@RequestParam(value = "p", required = true, defaultValue = "1") Integer page, @RequestParam(value = "kwd", required = true, defaultValue = "") String keyword, Model model) {
		Map<String, Object> map = boardService.getContentsList(page, keyword);
		
		model.addAttribute("map", map);
		// 참고: model.addAllAttributes(map);
		
		return "board/index";
	}

	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("boardVo", boardVo);
		
		return "board/view";
	}

	@RequestMapping("/delete/{no}")
	public String delete(HttpSession session, @PathVariable("no") Long boardNo, @RequestParam(value = "p", required = true, defaultValue = "1") Integer page, @RequestParam(value = "kwd", required = true, defaultValue = "") String keyword) {
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		boardService.deleteContents(boardNo, authUser.getNo());

		return "redirect:/board?p=" + page + "&kwd=" + WebUtil.encodeURL(keyword, "UTF-8");
	}

	@RequestMapping(value = "/update/{no}")
	public String update(HttpSession session, @PathVariable("no") Long no, Model model) {
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		BoardVo boardVo = boardService.getContents(no, authUser.getNo());
		model.addAttribute("boardVo", boardVo);

		return "board/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpSession session, BoardVo boardVo, @RequestParam(value = "p", required = true, defaultValue = "1") Integer page, @RequestParam(value = "kwd", required = true, defaultValue = "") String keyword) {
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		boardVo.setUserNo(authUser.getNo());
		boardService.updateContents(boardVo);

		return "redirect:/board/view/" + boardVo.getNo() + "?p=" + page + "&kwd=" + WebUtil.encodeURL(keyword, "UTF-8");
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(HttpSession session) {
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		return "board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(HttpSession session, @ModelAttribute BoardVo boardVo, @RequestParam(value = "p", required = true, defaultValue = "1") Integer page, @RequestParam(value = "kwd", required = true, defaultValue = "") String keyword) {
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		boardVo.setUserNo(authUser.getNo());
		boardService.addContents(boardVo);

		return "redirect:/board?p=" + page + "&kwd=" + WebUtil.encodeURL(keyword, "UTF-8");
	}

	@RequestMapping(value = "/reply/{no}")
	public String reply(HttpSession session, @PathVariable("no") Long no, Model model) {
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}

		BoardVo boardVo = boardService.getContents(no);
		boardVo.setOrderNo(boardVo.getOrderNo() + 1);
		boardVo.setDepth(boardVo.getDepth() + 1);

		model.addAttribute("boardVo", boardVo);

		return "board/reply";
	}
}
