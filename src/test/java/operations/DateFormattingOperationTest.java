package operations;

import com.ajira.test.fileoperations.operations.DateFormatOperation;
import com.ajira.test.fileoperations.operations.NewColumnOperation;
import com.ajira.test.fileoperations.util.CustomProperties;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DateFormattingOperationTest {
    private static final DateFormatOperation operation = new DateFormatOperation();

    @Test
    public void performOperation_validInputAndConfigData_returnFilteredData(){
        // Given:
        Map<String, String> originalMap = new LinkedHashMap<>();
        originalMap.put("first_name", "aa");
        originalMap.put("dob", "1992-06-12");

        Map<String, String> originalMap1 = new LinkedHashMap<>();
        originalMap1.put("first_name", "bb");
        originalMap1.put("dob", "1992-07-13");

        Map<String, String> originalMap2 = new LinkedHashMap<>();
        originalMap2.put("first_name", "cc");
        originalMap2.put("dob", "1992-08-14");

        List<Map<String, String>> originalData = new ArrayList<>();
        originalData.add(originalMap);
        originalData.add(originalMap1);
        originalData.add(originalMap2);

        Properties properties = mock(CustomProperties.class);
        when(properties.getProperty("columnToFormat")).thenReturn("dob");
        when(properties.getProperty("from")).thenReturn("yyyy-mm-dd");
        when(properties.getProperty("to")).thenReturn("dd-mm-yyyy");

        // When:
        List<Map<String, String>> modifiedData =
                operation.performOperation(originalData, properties);

        // Then:
        assertThat(modifiedData).isNotNull();
        assertThat(modifiedData.size()).isEqualTo(3);

        Map<String, String> modifiedMap = modifiedData.get(0);
        assertThat(modifiedMap.size()).isEqualTo(2);
        assertThat(modifiedMap.keySet()).containsExactly("first_name", "dob");
        assertThat(modifiedMap.values()).containsExactly("aa", "12-06-1992");

        Map<String, String> modifiedMap1 = modifiedData.get(1);
        assertThat(modifiedMap1.size()).isEqualTo(2);
        assertThat(modifiedMap1.keySet()).containsExactly("first_name", "dob");
        assertThat(modifiedMap1.values()).containsExactly("bb", "13-07-1992");

        Map<String, String> modifiedMap2 = modifiedData.get(2);
        assertThat(modifiedMap2.size()).isEqualTo(2);
        assertThat(modifiedMap2.keySet()).containsExactly("first_name", "dob");
        assertThat(modifiedMap2.values()).containsExactly("cc", "14-08-1992");
    }
}
