rd /s /q target
md target
java -Djava.ext.dirs=.. -jar mybatis-generator.jar -configfile generator.xml -overwrite