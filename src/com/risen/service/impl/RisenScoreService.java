package com.risen.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.common.page.Pagination;
import com.jeecms.core.dao.CmsDepartmentDao;
import com.jeecms.core.dao.CmsUserDao;
import com.risen.dao.IRisenScoreDao;
import com.risen.entity.RisenScore;
import com.risen.service.IRisenScoreService;

@Service
@Transactional
public class RisenScoreService implements IRisenScoreService {

	@Override
	public Pagination getPage(int pageNo, int pageSize, Integer userId) {
		// TODO Auto-generated method stub
		return dao.getPage(pageNo, pageSize, userId);
	}
	
	@Override
	public Pagination getPage(int pageNo, int pageSize, Integer userId,Integer departId) {
		// TODO Auto-generated method stub
		return dao.getPage(pageNo, pageSize, userId,departId);
	}
	@Override
	public Pagination getPage(int pageNo, int pageSize, Integer userId,Integer departId,String risenscYear) {
		return dao.getPage(pageNo, pageSize, userId,departId,risenscYear);
	}

	@Override
	public RisenScore save(RisenScore score) {
		// TODO Auto-generated method stub
		return dao.save(score);
	}

	@Override
	public RisenScore findById(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		dao.delete(id);
	}

	@Override
	public RisenScore update(RisenScore bean,Integer idms) {
		// TODO Auto-generated method stub
		return dao.update(bean,idms);
	}

	@Override
	public List<RisenScore> getTotalScore(Integer departId) {
		// TODO Auto-generated method stub
		List<RisenScore> scoreList = new ArrayList<RisenScore>();
		Pagination pagination = dao.getPage(1, 100000, null,departId);
		List<RisenScore> scores = (List<RisenScore>) pagination.getList();
		String userIds = "";
		if(scores.size()>0){
			for(RisenScore risenScore : scores){
				userIds = userIds + risenScore.getUser().getId()+",";
			}
			userIds = userIds.substring(0,userIds.length()-1);
			String[] users = userIds.split(",");
			List<String> stringId = Arrays.asList(users);
			Set<String> usersIds = new HashSet<String>();
			usersIds.addAll(stringId);
			for (String string : usersIds) {
				RisenScore afterScore = new RisenScore();
				Integer userId = new Integer(string);
				Pagination paginations = dao.getPage(1, 100000, userId,departId);
				List<RisenScore> scoress = (List<RisenScore>) paginations.getList();
				int totalScore = 0;
				for(RisenScore score : scoress){
					int scoreTemp = Integer.parseInt(score.getRisenScScore());
					totalScore+=scoreTemp;
				}
				afterScore.setUser(userDao.findById(userId));
				afterScore.setDepartId(departId);
				afterScore.setTotalScore(new Integer(totalScore));
				scoreList.add(afterScore);
			}
		}
		return scoreList;
	}
	
	@Override
	public boolean existScore(Integer userId, Integer departId, Integer quotaId,String risenqtYear) {
		// TODO Auto-generated method stub
		return dao.existScore(userId, departId, quotaId,risenqtYear);
	}
	
	@Autowired
	private IRisenScoreDao dao;
	@Autowired
	private CmsUserDao userDao;
	@Autowired
	private CmsDepartmentDao departDao;
	
}