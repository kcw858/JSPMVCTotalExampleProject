package com.sit.dao;

import java.util.*;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.sist.vo.GoodsVO;

import java.io.*;

public class GoodsDAO {
	private static SqlSessionFactory ssf;
	static
	{
		try
		{
			Reader reader = Resources.getResourceAsReader("Config.xml");
			ssf = new SqlSessionFactoryBuilder().build(reader);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/*
	 <select id="goodsListData" resultType="GoodslVO" parameterType="hashmap">
		SELECT no,goods_poster,goods_name
		FROM ${table}
		ORDER BY no ASC
		OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY
		</select>
		<select id="goodsTotalPage" resultType="int" parameterType="hashmap">
			SELECT CEIL(COUNT(*)/12.0)
			FROM ${table}
		</select>
	 */
	public static List<GoodsVO> goodsListData(Map map)
	{
		SqlSession session = ssf.openSession();
		List<GoodsVO> list = session.selectList("goodsListData",map);
		session.close();
		return list;
	}
	
	public static int goodsTotalPage(Map map)
	{
		SqlSession session = ssf.openSession();
		int total = session.selectOne("goodsTotalPage",map);
		session.close();
		return total;
	}
	
	/*
	 <update id="hitIncrement" parameterType="hashmap">
		UPDATE ${table} SET
		hit=hit+1
		WHERE no=${no}
		</update>
		<select id="goodsDetailData">
			SELECT *
			FROM ${table}
			WHERE no=#{no}
		</select>
	 */
	public static GoodsVO goodsDetailData(Map map)
	{
		SqlSession session = ssf.openSession();
		session.update("hitIncrement",map);
		session.commit();
		GoodsVO vo = session.selectOne("goodsDetailData",map);
		session.close();
		return vo;
	}
}
