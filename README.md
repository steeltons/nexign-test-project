<h2>CDR parser</h2>
<p>This is a simple program for parsing CDR(сall data record) files and creating phone reports.</p>

<h3>Instalation</h3>
<p>You can simply download the project folder and run it in you IDE.</p>

<h3>Usage</h3>
<p>To use parser put your file to parse into <b>/src/main/resourses/</b> and name it 
<b>report_example.txt</b></p>
<p>Then run MainApplication.java.</p>
<p>If you want to change input file name, you can change <b>INPUT_FILE_NAME</b> variable in 
MainApplication.java</p>

<h3>Input file</h3>
<p>Input file has specific format: <b>callType, phoneNumber, startCallingTime, endCallingTime, tariff</b></p>
<p>01, 73734435243, 20230725141448, 20230725142110, 11</p>
<p>02, 73160252296, 20231107161026, 20231107161906, 06</p>
<p>01, 71911814507, 20230207153216, 20230207154350, 11</p>
<p><b>callType</b> - type of call, where 01 - outgoing, 02 - incoming</p>
<p><b>phoneNumber</b> - phone number of format 7##########</p>
<p><b>startCallingTime and encCallingTime</b> - date and time of format YYYYMMDDHH24MMSS</p>
<p><b>tariff</b> - phone tariff. Realised 3 tariff types: 06 (No limit), 03 (Per minute) and 11 (Base)</p>

<h3>Tariff explanation</h3>
<p>No Limit tariff(06): first 300min are free, then every 1 minute will cost 1 rub.</p>
<p>Per minute tariff(03): every minute will cost 1.5 rub</p>
<p>Base tariff(11): first 100min will cost 0.5 rub per minute, then every 1 min will cost 1.5 rub.
 All incoming calls are free.</p>

<h3>Phone call cost algorithm</h3>
<p>All seconds are converted to minutes and rounded up.</p>
<p>For Per minute tariff cost is calculated on count of calls, that User made.</p>
<p>For the No limit tariff, the cost is calculated by the number of calls made after using 300 
 minutes of free limit, then 100 rubles are added - the cost of tariff.</p>
<p>For Base tariff cost is calculated on count of calls before 100min limit and after. But if User has
 0m and 30s of 0.5 rub call limit and he made a call of duration 2min, he will pay first 30sec for 0.5rub, and other
 1min 30sec for 3 rub. If he made a call of 50sec, he will pay only first 30sec. However if User has 
 less than 30sec in his limit and he made a call of duration 31 sec, his calls minutes now will cost per 1.5
 rub.</p>
 
<h3>Report file example</h3>
<p>
Tariff index: 06<br>
-------------------------------------------------------------------------------------------<br>
Report for phone number 70549903441:<br>
-------------------------------------------------------------------------------------------<br>
| Call Type |   Start Time        |     End Time        | Duration | Cost  |<br>
-------------------------------------------------------------------------------------------<br>
|     01    | 2023-01-13 06:32:00 | 2023-01-13 06:34:00 | 0:01:47 | 000,00 |<br>
|     01    | 2023-04-03 00:04:00 | 2023-04-03 00:13:00 | 0:08:12 | 000,00 |<br>
|     01    | 2023-07-07 19:50:00 | 2023-07-07 19:52:00 | 0:01:45 | 000,00 |<br>
|     01    | 2023-07-10 20:57:00 | 2023-07-10 21:04:00 | 0:07:37 | 000,00 |<br>
|     01    | 2023-07-12 01:55:00 | 2023-07-12 01:58:00 | 0:02:38 | 000,00 |<br>
|     01    | 2023-08-20 20:58:00 | 2023-08-20 21:03:00 | 0:05:09 | 000,00 |<br>
|     02    | 2023-11-19 18:56:00 | 2023-11-19 18:58:00 | 0:02:39 | 000,00 |<br>
|     02    | 2023-12-24 15:32:00 | 2023-12-24 15:32:00 | 0:00:11 | 000,00 |<br>
-------------------------------------------------------------------------------------------<br>
|                                           Total Cost: | 000100,00 rubles |<br>
-------------------------------------------------------------------------------------------<br>
</p>


