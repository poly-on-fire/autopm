package pull.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.ApplicationContext;

public class PullRoutes extends RouteBuilder {
    ApplicationContext applicationContext;

    public PullRoutes(ApplicationContext applicationContext) {
        super();
        this.applicationContext = applicationContext;
    }

    @Override
    public void configure() throws Exception {

        from("timer://barebones?fixedRate=true&period=360000").log("\n\n\t .... running  ........");

        from("file://propertyFiles/?noop=true").setHeader("fileKey").simple("${file:name.noext}")
                .process(new VelocityHeaderPropertiesProcessor()).wireTap("direct:a").wireTap("direct:to-a")
                .wireTap("direct:b").wireTap("direct:to-b")
                .log("VELOCITY CREATED ${file:name.noext} TOPIC");

        from("direct:a")
                .choice().when(header("topicType").regex("(Single Web Page|Dual Web Page)"))
                .to("velocity://velocity/a.html.vm")
                .to("file:./../infoclouddancer/public?fileName=z${header.fileKey}c.html");
        from("direct:to-a")
                .choice().when(header("topicType").regex("(Single Web Page|Dual Web Page)"))
                .to("velocity://velocity/to-a.html.vm")
                .to("file:./../infoclouddancer/public/src?fileName=to-z${header.fileKey}c.html");
        from("direct:b").choice().when(header("topicType").regex("(Dual Web Page)"))
                .to("velocity://velocity/b.html.vm")
                .to("file:./../infoclouddancer/public?fileName=z${header.fileKey}e.html");
        from("direct:to-b").choice().when(header("topicType").regex("(Dual Web Page)"))
                .to("velocity://velocity/to-b.html.vm")
                .to("file:./../infoclouddancer/public/src?fileName=to-z${header.fileKey}e.html");

    }
}
