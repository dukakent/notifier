# Notifier
The tool for sending messages

### Usage
You need to use flags for giving file paths and choose the way to send message
* `-d` or `--data` - specifies path to CSV file with users
* `-e` or `--email` - Send via email
* `-s` or `--sms` - Send via SMS

Example for sending both email and SMS:<br>
`notifier --data ./users.csv --email ./template_email.txt --sms ./template_sms.txt`

### For development
* First of all `mvn clean install`
* For creating new parsers you need to extend Parser class
* For adding new sending ways you need to extend Sender class
* Syntax of template properties: `${userName}`

### Project information
University: National Aerospace University "KhAI"<br>
Chair: Radiotechnical Systems of Aircraft<br>
Group: 545

### Team
Inokentii Duka<br>
Vladyslav Yermakov
