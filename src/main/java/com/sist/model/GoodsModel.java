package com.sist.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.vo.GoodsVO;
import com.sit.dao.GoodsDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class GoodsModel {
	private String[] table = {
		"",
		"goods_all",
		"goods_best",
		"goods_new",
		"goods_special",
	};
	
	private String[] title = {
			"",
			"전체 상품",
			"베스트 상품",
			"새 상품",
			"특별 상품",
		};
	
	@RequestMapping("goods/list.do")
	public String goods_List(HttpServletRequest request,HttpServletResponse response)
	{
		String page = request.getParameter("page");
		if(page == null)
			page ="1";
		
		String tno = request.getParameter("tno");
		if(tno ==null)
			tno ="1";
		
		int curpage = Integer.parseInt(page);
		
		Map map = new HashMap();
		map.put("table",table[Integer.parseInt(tno)]);
		map.put("start",(curpage*12)-12);
		
		List<GoodsVO> list = GoodsDAO.goodsListData(map);
		int totalpage = GoodsDAO.goodsTotalPage(map);
		
		//데이터를 전송 (출력 대상)
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("tno", tno);
		request.setAttribute("title", title[Integer.parseInt(tno)]);
		
		return "../goods/list.jsp";
	}
	
	@RequestMapping("goods/detail.do")
	public String goods_detail(HttpServletRequest request,HttpServletResponse response)
	{
		String no = request.getParameter("no");
		String tno = request.getParameter("tno");
		
		Map map = new HashMap();
		map.put("no", Integer.parseInt(no));
		map.put("table",table[Integer.parseInt(tno)]);
		
		GoodsVO vo = GoodsDAO.goodsDetailData(map);
		
		request.setAttribute("vo", vo);
		request.setAttribute("tno", tno);
		
		return "../goods/detail.jsp";
	}
}
