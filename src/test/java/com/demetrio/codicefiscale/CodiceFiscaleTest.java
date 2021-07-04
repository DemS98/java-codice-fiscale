package com.demetrio.codicefiscale;

import com.demetrio.codicefiscale.exception.BirthPlaceHomonymyException;
import com.demetrio.codicefiscale.exception.CodiceCatastaleNotFoundException;
import com.demetrio.codicefiscale.exception.InvalidPersonException;
import com.demetrio.codicefiscale.model.Sex;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

public class CodiceFiscaleTest {

    @Test
    public void checkCF() throws IOException {
        Assert.assertEquals("RSSMRA80A01F205X", CodiceFiscale.ofLazyInit()
                .withName("Mario")
                .withSurname("Rossi")
                .withBirthDay(1)
                .withBirthMonth(1)
                .withBirthYear(1980)
                .withBirthPlace("Milano")
                .withSex(Sex.M)
                .generate());

        // test with external API
//        try(InputStream jsonFile = this.getClass().getClassLoader()
//                .getResourceAsStream("mock_people.json")) {
//            JSONArray array = (JSONArray) new JSONTokener(jsonFile).nextValue();
//            int length = array.length();
//            for(int i=0;i<length;i++) {
//                JSONObject person = array.getJSONObject(i);
//
//                JSONObject jsonRequest = new JSONObject();
//                jsonRequest.put("name", person.getString("name"));
//                jsonRequest.put("surname", person.getString("surname"));
//                jsonRequest.put("gender", person.getString("sex"));
//                String[] splitDate = person.getString("birthDate").split("/");
//                jsonRequest.put("day",Integer.parseInt(splitDate[0]));
//                jsonRequest.put("month",Integer.parseInt(splitDate[1]));
//                jsonRequest.put("year",Integer.parseInt(splitDate[2]));
//                jsonRequest.put("city", person.getString("birthPlace"));
//                jsonRequest.put("province", person.getString("birthPlaceProvincia"));
//
//                String request = "https://apis.woptima.com/cf";
//                byte[] postData = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
//                int postDataLength = postData.length;
//                URL endpoint = new URL(request);
//                HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
//                connection.setDoOutput(true);
//                connection.setInstanceFollowRedirects(false);
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setRequestProperty("Content-Length", postDataLength+"");
//                connection.setUseCaches(false);
//                try(OutputStream output = connection.getOutputStream()) {
//                    output.write(postData);
//                }
//                try(InputStream input = connection.getInputStream()) {
//                    JSONObject object = (JSONObject) new JSONTokener(input).nextValue();
//                    Assert.assertEquals(object.getString("cf"),
//                            CodiceFiscale.generate(person.getString("name"), person.getString("surname"),
//                                    LocalDate.parse(person.getString("birthDate"), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
//                                    person.getString("birthPlace"), person.getString("birthPlaceProvincia"),
//                                    Sex.valueOf(person.getString("sex"))));
//                }
//            }
//        }
    }

    @Test(expected = CodiceCatastaleNotFoundException.class)
    public void checkCodiceCatastaleNotFoundException() {
        CodiceFiscale.ofLazyInit()
            .withName("Mario")
            .withSurname("Rossi")
            .withBirthDate(LocalDate.of(1998,10,11))
            .withBirthPlace("Imaginary")
            .withBirthPlaceProvince("IM")
            .withSex(Sex.M)
            .generate();
    }

    @Test(expected = BirthPlaceHomonymyException.class)
    public void checkBirthPlaceHomonymyException() {
        CodiceFiscale.ofLazyInit()
                .withName("Mario")
                .withSurname("Rossi")
                .withBirthDay(11)
                .withBirthMonth(10)
                .withBirthYear(1998)
                .withBirthPlace("Samone")
                .withSex(Sex.M)
                .generate();
    }

    @Test(expected = InvalidPersonException.class)
    public void checkSurnameInvalidNameException() {
        CodiceFiscale.ofLazyInit()
                .withName("Mr'+iù")
                .withSurname("Rossi")
                .withBirthDate(LocalDate.of(1998,10,11))
                .withBirthPlace("Imaginary")
                .withBirthPlaceProvince("IM")
                .withSex(Sex.M)
                .generate();
    }

    @Test(expected = InvalidPersonException.class)
    public void checkNoBirthDateException() {
        CodiceFiscale.ofLazyInit()
                .withName("Mario")
                .withSurname("Rossi")
                .withBirthPlace("Imaginary")
                .withBirthPlaceProvince("IM")
                .withSex(Sex.M)
                .generate();
    }
}
