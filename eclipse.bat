mvn eclipse:eclipse -DdownloadSources=true -Dwtpversion=2.0 
-- eclipse:clean
-- mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.3 -Dpackaging=jar -Dfile=ojdbc6.jar -DgeneratePom=true