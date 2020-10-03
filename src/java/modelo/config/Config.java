package modelo.config;

import java.io.IOException;
import java.util.Properties;

public class Config {
    
    public static final String JDBC_DRIVER = getValor("jdbc.driver");
    public static final String JDBC_URL = getValor("jdbc.url");
    public static final String JDBC_USUARIO = getValor("jdbc.usuario");
    public static final String JDBC_SENHA = getValor("jdbc.senha");
    
    private Config(){
    
    }
    
    private static String getValor(String chave){
        Properties properties = new Properties();
        try {
            properties.load(Config.class.getResourceAsStream("config.properties"));
            return properties.getProperty(chave);
        } catch (IOException ex) {
            return null;
        }
    }
}
