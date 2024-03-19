package com.example.pm1e2grupo4.Config;

public class RestApiMethods {

    public static final String EndpointPostPerson = "http://192.168.1.35/Api_Examen2/createperson.php";
    private static final String BASE_ENDPOINT = "http://192.168.1.35/Api_Examen2/";
    public static final String Endpointplace = "https://jsonplaceholder.typicode.com/posts";

    public static String extraerEndpoint() {

        return BASE_ENDPOINT;
    }

}
