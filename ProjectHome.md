# What is Zipscript? #
|![http://www.zipscript.org/zipscript.gif](http://www.zipscript.org/zipscript.gif)|A full-featured expression language and Java-based multipurpose evaluation engine.  More details can be found in the guides and wiki (see _Links_ on the right side of your screen)|
|:--------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

**Version 0.9.2 was released 7/31/2008** - See [Roadmap](Roadmap.md) for details on the next release

# How could you use Zipscript? #
  * As a template engine for a MVC web application
  * As a utility to evaluate conditional expressions from external data
  * As a template language for non web-oriented needs
see the CheatSheet for more details...

# Is there an IDE for Zipscript? #
There isn't an IDE yet but an Eclipse plugin will be coming soon.  If you want to get a feel of what it will be like, you can reference the other script-related eclipse plugins I have created:
  * [FreeMarker IDE](http://freemarker-ide.sourceforge.net/) (now moved to [JBoss Freemarker](http://repository.jboss.org/eclipse/freemarker-ide/)) for [FreeMarker](http://www.freemarker.org)
  * [VelocityWebEdit](http://velocitywebedit.sourceforge.net/) for [Velocity](http://velocity.apache.org/)

# Why would you use Zipscript over languages like Freemarker or Velocity? #
First, I want to say that I hold the other languages in high regard which is why I wrote the Eclipse plugins for them.  I had a desire to have a language like the FM language but had additional nesting capabilities and other features like being able to get more than just strings as merge results.  In terms of functionality, an whole new world of options is now available to create true components that can evaluate internal data structures (see YUI macrolib download).  ZipScript is an evolution over FreeMarker just like FreeMarker is an evolution over Velocity.

# Why would you not use Zipscript? #
If you are completely into JSP or JSF for web development, this probably won't change your mind.  I, personally, don't like expression languages that are built with XML because of the need to escape commonly-used operators because they are written in node attributes.  I also like having the ability to use quotation marks to represent strings and, with XML, you lose that ability because XML attributes are already in quotation marks and, additionally, prefer a language syntax that is visually different ('[.md](.md)' instead of '<>') than the (most common) output which is XML/HTML.

### What Now? ###
Check out the SyntaxExamples
or
download it (see featured downloads on the right of your screen) and check out the [Template Author's Guide](http://www.zipscript.org/layout/frameset.html?http://www.zipscript.org/docs/core/bk01.html) and [Developer's Guide](http://www.zipscript.org/layout/frameset.html?http://www.zipscript.org/docs/core/bk02.html) or, if you just want to get up and running quickly, check out the CheatSheet