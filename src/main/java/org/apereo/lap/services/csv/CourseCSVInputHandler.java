/**
 * Copyright 2013 Unicon (R) Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.apereo.lap.services.csv;

import au.com.bytecode.opencsv.CSVReader;
import org.apereo.lap.services.ConfigurationService;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Types;

public class CourseCSVInputHandler extends BaseCSVInputHandler {

    public static final String FILENAME = "course.csv";

    static final String SQL_INSERT_COURSE = "INSERT INTO COURSE (COURSE_ID,SUBJECT,ENROLLMENT,ONLINE_FLAG) VALUES (?,?,?,?)";

    static final int[] SQL_TYPES_COURSE = new int[] {
            // COURSE_ID,SUBJECT,ENROLLMENT,ONLINE_FLAG
            Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.BOOLEAN
    };

    public CourseCSVInputHandler(ConfigurationService configuration, JdbcTemplate jdbcTemplate) {
        super(configuration, jdbcTemplate);
    }

    @Override
    public String makeInsertSQL() {
        return SQL_INSERT_COURSE;
    }

    @Override
    public int[] makeInsertSQLParams() {
        return SQL_TYPES_COURSE;
    }

    @Override
    public String getCSVFilename() {
        return FILENAME;
    }

    @Override
    public CSVReader readCSV(boolean reRead) {
        return readCSV(4, "COURSE_ID", reRead);
    }

    @Override
    public ReadResult readInputIntoDB() {
        CSVReader reader = readCSV(false);
        return readCSVFileIntoDB(reader);
    }

    @Override
    public Object[] validateAndConvertParams(String[] csvLine) {
        assert csvLine != null && csvLine.length > 0;
        Object[] params = new Object[csvLine.length];
        params[0] = parseString(csvLine[0], null, true, "COURSE_ID");
        params[1] = parseString(csvLine[1], null, false, "SUBJECT");
        params[2] = parseInt(csvLine[2], 0, null, false, "ENROLLMENT");
        params[3] = parseBoolean(csvLine[3], false, "ONLINE_FLAG");
        return params;
    }

}
