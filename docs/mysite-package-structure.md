### Mysite04, 05 Package Structure
<pre>
[src]
   |--- [main]
                 |--- [java]
                 |	         |--- com 
                 |	         |		|--- poscoict
                 |	         |		|	|--- config
                 |	         |	|	|	|--- app
                 |	         |	|	|	|             |--- DBConfig.java
                 |	         |	|	|	|             |--- MyBatisConfig.java
                 |	         |	|	|	|--- web
                 |	         |	|	|	|             |--- MVCConfig.java
                 |	         |	|	|	|             |--- SecurityConfig.java
                 |	         |	|	|	|             |--- MessageConfig.java
                 |	         |	|	|	|             |--- FileuploadConfig.java
                 |	         |	|	|--- mysite
                 |	         |	|	|	|--- controller
                 |	         |	|	|	|--- service
                 |	         |	|	|	|--- repository
                 |	         |	|	|	|--- vo
                 |	         |	|	|	|--- exception
                 |	         |	|	|	|--- aop
                 |	         |	|	|	|--- config
                 |	         |	|	|	|	|--- AppConfig.java
                 |	         |	|	|	|	|--- WebConfig.java
                 |--- [resources]
                 |	         |--- logback.xml	
                 |	         |--- com 
                 |	         |	|--- poscoict
                 |	         |	|	|--- mysite
                 |	         |	|	|	|--- config
                 |	         |	|	|	|	|--- app
                 |	         |	|	|	|	|           |-- jdbc.properties
                 |	         |	|	|	|	|--- web
<pre>                 