import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * 
 */

/**
 * @author Neytirix
 *
 */
public class Start {

	/**
	 * @param args
	 */
	private static String key = "4054d80e-2a76-419d-a53d-773001317c76";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] champions = getChampionIDs();
		System.out.println("success.");
	}
	
	private static int[] getChampionIDs () {
		String urlStr = "https://na.api.pvp.net/api/lol/na/v1.2/champion?api_key=" + key;
		URL url;
		String jsonString = "";

		try {
			url = new URL(urlStr);

			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			// 设置连接属性
			httpConn.setConnectTimeout(6000);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			// 获取相应码
			int respCode = httpConn.getResponseCode();
			if (respCode == 200) {
				jsonString = ConvertStream2Json(httpConn.getInputStream());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JSONObject jsonObj = new JSONObject(jsonString);
		JSONArray result = jsonObj.getJSONArray("champions");
		
		TreeMap<Integer, JSONObject> champions = new TreeMap<Integer, JSONObject>();
		for (int i = 0; i < result.length(); i++) {
			int id = result.getJSONObject(i).getInt("id");
			champions.put(id, getChampionInfo(id));
			System.out.println(id);
		}
		return null;
	}
	
	private static JSONObject getChampionInfo(int id) {
		String urlStr = "https://global.api.pvp.net/api/lol/static-data/na/v1.2/champion/1?champData=spells&api_key=" + key;
		URL url;
		String jsonString = "";

		try {
			url = new URL(urlStr);

			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			// 设置连接属性
			httpConn.setConnectTimeout(6000);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			// 获取相应码
			int respCode = httpConn.getResponseCode();
			if (respCode == 200) {
				jsonString = ConvertStream2Json(httpConn.getInputStream());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JSONObject jsonObj = new JSONObject(jsonString);
		return jsonObj;
	}
	
	private static String ConvertStream2Json(InputStream inputStream) {
        String jsonStr = "";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        try
        {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
            {
                out.write(buffer, 0, len);
            }

            jsonStr = new String(out.toByteArray());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonStr;
    }

}
