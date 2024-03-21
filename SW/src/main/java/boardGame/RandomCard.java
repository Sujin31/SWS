package boardGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONObject;

public class RandomCard {
	private static List<String> places = new ArrayList<String>(Arrays.asList("은행","회사파티","카지노","레스토랑","서커스텐트"));
	private static List<List<String>> jobs = new ArrayList<List<String>>(Arrays.asList(	
													new  ArrayList<String>( Arrays.asList("은행장", "무장차량운전수", "손님", "창구직원", "청원경찰", "강도", "진상손님")),
													new  ArrayList<String>( Arrays.asList("초청가수", "비서", "회계사", "초대 못 받은 사람", "사장", "이사", "퇴사 일주일 전 사원")),
													new  ArrayList<String>( Arrays.asList("바텐더", "딜러", "도박꾼", "매니저", "바람잡이", "빈털털이", "호구")),
													new  ArrayList<String>( Arrays.asList("뮤지션", "손님", "비평가", "도어맨", "웨이터", "셰프", "수셰프")),
													new  ArrayList<String>( Arrays.asList("광대", "똑똑한 원숭이", "감독", "마술사", "조련사", "저글러", "관객"))
										          ));
	
	
	public static JSONObject command(String cmd, int totalNum) {
		JSONObject json = new JSONObject();
		//System.out.println(cmd);
		//시작
		if(cmd.equals("/시작")) {
			System.out.println("/시작입니다");
			json = start(totalNum);
			return json;
		}
		
		return json;
	}
	
	/*
	 * 게임시작 전 준비작업있음
	 * @param totalNum 전체 인원
	 * */
	public static JSONObject start(int totalNum) {
		
		String curPlace;
		List<String> curJob = new ArrayList<String>();
		
		Random random = new Random();
		int idx = random.nextInt(places.size());
		curPlace = places.get(idx);
		curJob = jobs.get(idx);
		places.remove(idx);
		jobs.remove(idx);
		
		//직업 인원수 맞추기
		Collections.shuffle(curJob);
		curJob = curJob.subList(0, totalNum - 1);//한명은 스파이
		
		//스파이 넣기
		curJob.add("스파이");
		Collections.shuffle(curJob);
		
		//json으로 넘겨준다.
		JSONObject json = new JSONObject();
		json.put("place", curPlace);
		json.put("job", curJob);
		
		return json;
	}
	
}
