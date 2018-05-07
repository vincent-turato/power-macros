# PowerMacros
PowerMacros is plug-in for BurpSuite proxy. It provides extended functionality of BurpSuite's built-in macros feature. This extension allows you to pipe automated requests (when certain conditions are met) to JavaScript files for pre-processing before being sent outbound. With JavaScript files you load, you can manipulate Burp Suite's automated requests on the fly. PowerMacros integrates powerfully with Burp Suite's features, in particular its target scoping functionality. 

## Practical Uses
With the use of JavaScript files, fine-tuned manipulation of automated requests can be achieved. This means that there are many ways this tool can be used. Here are just a few examples of PowerMacros capabilities:

- Dynamic generation of injection (e.g. SQL) strings
- Dynamic generation and replacement of CSRF tokens
- Tailoring an XSS string to individual automated requests.



## Features
- Modification of automated outbound request
- Extracting data from an arbitrary response
- Adding new headers
- Modifying existing headers
  
## Advantages over BurpSuite's built-in macros
- Ability to perform header/body string modification of automated requests
- Ability to add new headers into the requests.

## How to
### Installation of PowerMacros
1. Download Burp Suite at: https://portswigger.net/burp
2. In the "Extender" tab, click the "Add" button to add the PowerMacros extension. In the window that pops up, leave the extension type as Java and load the PowerMacros .jar file.
3. PowerMacros is now successfully installed. A new tab has been added to Burp Suite called "PowerMacros".
### PowerMacros Configuration
In the "PowerMacros" tab, you'll see two inner tabs: "Extract" and "Replace". You will first need to set up string extractions before you can link string replacement scripts to them.

Extraction Configuration:
1. In the "Extract" tab, click "Add extraction". 
2. For this simple tutorial, set the extraction type to Regex and enter a Regex pattern that will be searched for in automated requests. (Advanced extraction: you can load a JavaScript file to perform dynamic extraction instead of a static Regex pattern)

Replacement Configuration:
1. In the "Replace" tab, click "Add replacement". 
2. Set the replacement type to "JavaScript" and load the script file you will use to perform dynamic replacement.
3. Before you click save, you must link your extraction to this replacement. You'll see that you can add linked extractions in the bottom half of that window.

### Linking the Replacement with BurpSuite
1. Click the "Project Options". Within that tab, click the inner "Sessions" tab.
2. In the "Session Handling Rules" section, click "Add". A new window should pop up.
3. In the scope tab, you can set up advanced matching Regex conditions of URLs, IPs, ports, and file names.
3. Under the "Rule Actions" section, click "Add" > "Invoke a Burp Extension".
4. Select the replacement you set up and click "Ok".


## Screenshots

Advanced URL Scoping Functionality. When the "in scope" URL conditions are met, user-specified string manipulation of outgoing requests will be triggered.

![Session handling rules](/screenshots/0_session_handling_rule_3_advanced.png?raw=true "Main tab")

Main: Example Extractions

![Extract tab](/screenshots/0_main_extract_example.png?raw=true "Logger tab")

Main: Example Replacements

![Replace tab](/screenshots/0_main_replace_example.png?raw=true "Settings tab")