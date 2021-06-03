package com.example.demo.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.FacilityModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.FacilityService;
import com.example.demo.service.UserService;

@Component
public class Calculator {
	@Autowired
	FacilityService facilityService;

	@Autowired
	UserService userService;

	String API_Key = "";
	
	public List<FacilityModel> getPoint(String string) {
		List<UserModel> users = userService.printUserByCond(string);
		System.out.println("users size : " +users.size() +"string : [" + string + "]");
		List<FacilityModel> m_facility ;
		
		if(string != null)
			m_facility = facilityService.printBestLandmark(string);
		else
			m_facility = facilityService.printBestFacility();
		
		FacilityModel a = new FacilityModel();
		double lat =0; double lng =0;
		for(int i=0;i<users.size();i++){
			lat += users.get(i).getLatitude();
			lng += users.get(i).getLongitude();
		}
		lat/= users.size();
		lng/= users.size();
		a.setLatitude(lat);
		a.setLongitude(lng);
		a.setName("x");
		
		m_facility.add(a);

		setTime(users, m_facility);

		Collections.sort(m_facility, new Comparator<FacilityModel>() {
			@Override
			public int compare(FacilityModel f1, FacilityModel f2) {
				if (square(f1.getTime()) > square(f2.getTime()))
					return 1;
				else if (f1.getTime() < f2.getTime())
					return -1;
				else
					return 0;
			}
		});
		
		for(int i=0;i<m_facility.size();i++) {
			String s = "";
			for(int j=0;j<users.size()-1;j++) {
				s += users.get(j).getIdent() + ": ";
				s += (int)Math.cbrt(m_facility.get(i).getEachTime().get(j))+"분, ";
			}
			s += users.get(users.size()-1).getIdent() + ": ";
			s += (int)Math.cbrt(m_facility.get(i).getEachTime().get(users.size()-1)) +"분";
			m_facility.get(i).setTimeList(s);
			System.out.println(s);
		}
		/*
		String keyword = "restaurants";
		String http2 = makeHttp3(m_facility.get(0).getLatitude(), m_facility.get(0).getLongitude(), keyword);
		String httpres2 = httpRequest(http2);

		List<FacilityModel> m_facility2 = new ArrayList<FacilityModel>();

		try {
			JSONParser jsonParser = new JSONParser();

			JSONObject jsonObject = (JSONObject) jsonParser.parse(httpres2);

			JSONArray resultsArray = (JSONArray) jsonObject.get("results");

			for (int i = 0; i < resultsArray.size(); i++) {
				FacilityModel facilityModel = new FacilityModel();
				JSONObject resultsObject = (JSONObject) resultsArray.get(i);

				System.out.println(resultsObject);

				facilityModel.setLocation((String) resultsObject.get("formatted_address"));

				JSONObject geometryObject = (JSONObject) resultsObject.get("geometry");
				JSONObject locationObject = (JSONObject) geometryObject.get("location");

				facilityModel.setLatitude((double) Double.parseDouble(String.valueOf(locationObject.get("lat"))));
				facilityModel.setLongitude((double) Double.parseDouble(String.valueOf(locationObject.get("lng"))));
				facilityModel.setName((String) resultsObject.get("name"));

				try {
					facilityModel.setDistance((double) resultsObject.get("rating"));	
				} catch (java.lang.ClassCastException e) {
					facilityModel.setDistance(0);
				}

				m_facility2.add(facilityModel);

				System.out.println(facilityModel.getFacility_name() + ", " + facilityModel.getDistance());
			}
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		setTime(users, m_facility2);

		Collections.sort(m_facility2, new Comparator<FacilityModel>() {
			@Override
			public int compare(FacilityModel f1, FacilityModel f2) {
				if (f1.getDistance() < f2.getDistance())
					return 1;
				else if (f1.getDistance() > f2.getDistance())
					return -1;
				else
					return 0;
			}
		});*/
		
		m_facility = m_facility.subList(0, 5);

		return m_facility;
	}

	public void setTime(List<UserModel> users, List<FacilityModel> m_facility) {
		String http[] = makeHttp2(users, m_facility);
		int block = (100 / users.size() <= 25) ? 100 / users.size() : 25;

		for (int k = 0; k < http.length; k++) {
			String httpres = httpRequest(http[k]);
			System.out.println("httpres: " + httpres);

			try {
				JSONParser jsonParser = new JSONParser();

				JSONObject jsonObject = (JSONObject) jsonParser.parse(httpres);

				JSONArray rowsArray = (JSONArray) jsonObject.get("rows");

				for (int i = 0; i < rowsArray.size(); i++) {
					int timesum = 0;
					JSONObject rowsObject = (JSONObject) rowsArray.get(i);
					JSONArray elementsArray = (JSONArray) rowsObject.get("elements");

					for (int j = 0; j < elementsArray.size(); j++) {
						JSONObject elementsObject = (JSONObject) elementsArray.get(j);
						if (!((String) elementsObject.get("status")).equals("ZERO_RESULTS")) {
							JSONObject durationObject = (JSONObject) elementsObject.get("duration");

							int time = timeToInt((String) durationObject.get("text"));
							time = time*time*time;
							m_facility.get(i + k * block).setEachTime(time);
							timesum += time;
							System.out.print("time : " + time + " ");
						} else
							m_facility.get(i + k * block).setEachTime(9999);
							timesum += 9999;
					}

					System.out.println(m_facility.get(i + k * block).getFacility_name() + ": " + timesum);
					m_facility.get(i + k * block).setTime(timesum);
				}
			} catch (org.json.simple.parser.ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public String makeHttp(double[] latitude, double[] longitude) {
		String s = "";

		s = "https://maps.googleapis.com/maps/api/directions/json?origin=" + Double.toString(latitude[0]) + ','
				+ Double.toString(longitude[0]) + "&destination=" + Double.toString(latitude[1]) + ','
				+ Double.toString(longitude[1]) + "&mode=transit&language=ko&departure_time=now&key=" + API_Key;

		return s;
	}
//
	public String[] makeHttp2(List<UserModel> users, List<FacilityModel> m_facility) {
		int block = (100 / users.size() <= 25) ? 100 / users.size() : 25;
		int size = (int) Math.ceil((double) m_facility.size() / (double) block);
		int rest = m_facility.size() % block;
		String[] sArray = new String[size];

		System.out.println("usersize: " + users.size() + ", m_facilitysize: " + m_facility.size() + ", size: " + size
				+ ", block: " + block + ", rest: " + rest);

		String s = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";

		for (int j = 0; j < size; j++) {
			sArray[j] = s;
			sArray[j] += m_facility.get(block * j).getLatitude() + "," + m_facility.get(block * j).getLongitude();

			for (int i = 1; i < ((j == size - 1 && rest != 0) ? rest : block); i++) {
				sArray[j] += "|" + m_facility.get(i + block * j).getLatitude() + ","
						+ m_facility.get(i + block * j).getLongitude();
			}

			sArray[j] += "&destinations=" + users.get(0).getLatitude() + "," + users.get(0).getLongitude();

			for (int i = 1; i < users.size(); i++) {
				sArray[j] += "|" + users.get(i).getLatitude() + "," + users.get(i).getLongitude();
			}

			sArray[j] += "&mode=transit&language=ko&departure_time=now&key=" + API_Key;

			System.out.println("direction matrix http" + j + ": " + sArray[j]);
		}
		return sArray;
	}

	public String makeHttp3(double latitude, double longitude, String keyword) {
		String s = "";
		s = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + keyword + "&location=" + latitude
				+ "," + longitude + "&language=ko&key=" + API_Key;

		System.out.println("place http: " + s);

		return s;
	}

	// 제이슨 받아오기
	@SuppressWarnings("static-access")
	public String httpRequest(String s) {
		String receiveMsg = "";
		String str;
		URL url = null;
		try {
			url = new URL(s);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			if (conn.getResponseCode() == conn.HTTP_OK) {
				InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
				BufferedReader reader = new BufferedReader(tmp);
				StringBuffer buffer = new StringBuffer();
				while ((str = reader.readLine()) != null) {
					buffer.append(str);
				}
				receiveMsg = buffer.toString();

				reader.close();
			} else {
				// Log.i("통신 결과",conn.getResponseCode()+"에러");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return receiveMsg;
	}

	// 시간 형변환
	public int timeToInt(String s) {
		int result = 0;
		String[] split = s.split("일 |시간 |분");

		if (split.length == 1) {
			// try {
			result = Integer.parseInt(split[0]);
			// }catch(Exception e){
			// return 0;
			// }
		} else if (split.length == 2) {
			result = Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
		} else {
			result = Integer.parseInt(split[0]) * 1440 + Integer.parseInt(split[1]) * 60 + Integer.parseInt(split[2]);
		}

		return result;
	}

	// 시간 계산
	public String jsonTimeParsing(String s) {
		String result = "";
		try {
			JSONParser jsonParser = new JSONParser();

			JSONObject jsonObject = (JSONObject) jsonParser.parse(s);

			JSONArray routesArray = (JSONArray) jsonObject.get("routes");

			for (int i = 0; i < routesArray.size(); i++) {
				JSONObject legsObject = (JSONObject) routesArray.get(i);
				JSONArray legsArray = (JSONArray) legsObject.get("legs");

				for (int j = 0; j < legsArray.size(); j++) {
					JSONObject durationObject = (JSONObject) legsArray.get(j);
					JSONObject textObject = (JSONObject) durationObject.get("duration");

					result = (String) textObject.get("text");
					System.out.println("시간: " + result);
				}
			}
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 대중교통 횟수 계산
	public int jsonTransitParsing(String s) {
		int count = 0;

		try {
			JSONParser jsonParser = new JSONParser();

			JSONObject jsonObject = (JSONObject) jsonParser.parse(s);

			JSONArray routesArray = (JSONArray) jsonObject.get("routes");

			for (int i = 0; i < routesArray.size(); i++) {
				JSONObject legsObject = (JSONObject) routesArray.get(i);
				JSONArray legsArray = (JSONArray) legsObject.get("legs");

				for (int j = 0; j < legsArray.size(); j++) {
					JSONObject durationObject = (JSONObject) legsArray.get(j);
					JSONArray stepsArray = (JSONArray) durationObject.get("steps");

					for (int k = 0; k < stepsArray.size(); k++) {
						JSONObject stepsObject = (JSONObject) stepsArray.get(k);

						if (((String) stepsObject.get("travel_mode")).equals("TRANSIT"))
							count++;
					}
				}
			}
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return count;
	}

	// 길찾기
	public void jsonParsing(String s) {
		try {
			JSONParser jsonParser = new JSONParser();

			JSONObject jsonObject = (JSONObject) jsonParser.parse(s);

			JSONArray routesArray = (JSONArray) jsonObject.get("routes");

			for (int i = 0; i < routesArray.size(); i++) {
				JSONObject legsObject = (JSONObject) routesArray.get(i);
				JSONArray legsArray = (JSONArray) legsObject.get("legs");

				for (int j = 0; j < legsArray.size(); j++) {
					JSONObject durationObject = (JSONObject) legsArray.get(j);
					JSONArray stepsArray = (JSONArray) durationObject.get("steps");

					for (int k = 0; k < stepsArray.size(); k++) {
						JSONObject stepsObject = (JSONObject) stepsArray.get(k);

						JSONObject distanceTextObject = (JSONObject) stepsObject.get("distance");
						System.out.println("거리: " + distanceTextObject.get("text"));

						JSONObject durationTextObject = (JSONObject) stepsObject.get("duration");
						System.out.println("시간: " + durationTextObject.get("text"));

						JSONObject end_locationTextObject = (JSONObject) stepsObject.get("end_location");
						System.out.println(
								"도착지: " + end_locationTextObject.get("lat") + " " + end_locationTextObject.get("lng"));

						System.out.println("방법: " + stepsObject.get("html_instructions"));

						System.out.println();
					}
				}
			}
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	}
	
	private int square(int time) {
		return time*time;
	}
}
