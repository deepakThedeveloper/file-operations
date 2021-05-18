package operations;

import com.ajira.test.fileoperations.operations.CombineColumnsOperation;
import com.ajira.test.fileoperations.operations.DataFilteringOperation;
import com.ajira.test.fileoperations.util.CustomProperties;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataFilteringOperationTest {
    private static final DataFilteringOperation operation = new DataFilteringOperation();

    @Test
    public void performOperation_validInputAndConfigDataNumericFilter_returnFilteredData(){
        // Given:
        Map<String, String> originalMap = new LinkedHashMap<>();
        originalMap.put("first_name", "aa");
        originalMap.put("age", "50");

        Map<String, String> originalMap1 = new LinkedHashMap<>();
        originalMap1.put("first_name", "bb");
        originalMap1.put("age", "51");

        Map<String, String> originalMap2 = new LinkedHashMap<>();
        originalMap2.put("first_name", "cc");
        originalMap2.put("age", "52");

        List<Map<String, String>> originalData = new ArrayList<>();
        originalData.add(originalMap);
        originalData.add(originalMap1);
        originalData.add(originalMap2);

        Properties properties = mock(CustomProperties.class);
        when(properties.getProperty("columnType")).thenReturn("numeric");
        when(properties.getProperty("columnToFilter")).thenReturn("age");
        when(properties.getProperty("condition")).thenReturn("greater than");
        when(properties.getProperty("value")).thenReturn("50");

        // When:
        List<Map<String, String>> modifiedData =
                operation.performOperation(originalData, properties);

        // Then:
        assertThat(modifiedData).isNotNull();
        assertThat(modifiedData.size()).isEqualTo(2);

        Map<String, String> modifiedMap = modifiedData.get(0);
        assertThat(modifiedMap.size()).isEqualTo(2);
        assertThat(modifiedMap.keySet()).containsExactly("first_name", "age");
        assertThat(modifiedMap.values()).containsExactly("bb", "51");

        Map<String, String> modifiedMap1 = modifiedData.get(1);
        assertThat(modifiedMap1.size()).isEqualTo(2);
        assertThat(modifiedMap1.keySet()).containsExactly("first_name", "age");
        assertThat(modifiedMap1.values()).containsExactly("cc", "52");
    }

    @Test
    public void performOperation_validInputAndConfigDataNumericFilterLessThan_returnFilteredData(){
        // Given:
        Map<String, String> originalMap = new LinkedHashMap<>();
        originalMap.put("first_name", "aa");
        originalMap.put("age", "50");

        Map<String, String> originalMap1 = new LinkedHashMap<>();
        originalMap1.put("first_name", "bb");
        originalMap1.put("age", "51");

        Map<String, String> originalMap2 = new LinkedHashMap<>();
        originalMap2.put("first_name", "cc");
        originalMap2.put("age", "49");

        List<Map<String, String>> originalData = new ArrayList<>();
        originalData.add(originalMap);
        originalData.add(originalMap1);
        originalData.add(originalMap2);

        Properties properties = mock(CustomProperties.class);
        when(properties.getProperty("columnType")).thenReturn("numeric");
        when(properties.getProperty("columnToFilter")).thenReturn("age");
        when(properties.getProperty("condition")).thenReturn("lesser than equals");
        when(properties.getProperty("value")).thenReturn("50");

        // When:
        List<Map<String, String>> modifiedData =
                operation.performOperation(originalData, properties);

        // Then:
        assertThat(modifiedData).isNotNull();
        assertThat(modifiedData.size()).isEqualTo(2);

        Map<String, String> modifiedMap = modifiedData.get(0);
        assertThat(modifiedMap.size()).isEqualTo(2);
        assertThat(modifiedMap.keySet()).containsExactly("first_name", "age");
        assertThat(modifiedMap.values()).containsExactly("aa", "50");

        Map<String, String> modifiedMap1 = modifiedData.get(1);
        assertThat(modifiedMap1.size()).isEqualTo(2);
        assertThat(modifiedMap1.keySet()).containsExactly("first_name", "age");
        assertThat(modifiedMap1.values()).containsExactly("cc", "49");
    }

    @Test
    public void performOperation_validInputAndConfigDataTextFilter_returnFilteredData(){
        // Given:
        Map<String, String> originalMap = new LinkedHashMap<>();
        originalMap.put("first_name", "aa");
        originalMap.put("age", "50");

        Map<String, String> originalMap1 = new LinkedHashMap<>();
        originalMap1.put("first_name", "bb");
        originalMap1.put("age", "51");

        Map<String, String> originalMap2 = new LinkedHashMap<>();
        originalMap2.put("first_name", "aa");
        originalMap2.put("age", "52");

        List<Map<String, String>> originalData = new ArrayList<>();
        originalData.add(originalMap);
        originalData.add(originalMap1);
        originalData.add(originalMap2);

        Properties properties = mock(CustomProperties.class);
        when(properties.getProperty("columnType")).thenReturn("text");
        when(properties.getProperty("columnToFilter")).thenReturn("first_name");
        when(properties.getProperty("condition")).thenReturn("equals");
        when(properties.getProperty("value")).thenReturn("aa");

        // When:
        List<Map<String, String>> modifiedData =
                operation.performOperation(originalData, properties);

        // Then:
        assertThat(modifiedData).isNotNull();
        assertThat(modifiedData.size()).isEqualTo(2);

        Map<String, String> modifiedMap = modifiedData.get(0);
        assertThat(modifiedMap.size()).isEqualTo(2);
        assertThat(modifiedMap.keySet()).containsExactly("first_name", "age");
        assertThat(modifiedMap.values()).containsExactly("aa", "50");

        Map<String, String> modifiedMap1 = modifiedData.get(1);
        assertThat(modifiedMap1.size()).isEqualTo(2);
        assertThat(modifiedMap1.keySet()).containsExactly("first_name", "age");
        assertThat(modifiedMap1.values()).containsExactly("aa", "52");
    }

    @Test
    public void performOperation_validInputAndConfigDataTextFilterEmptyName_returnFilteredData(){
        // Given:
        Map<String, String> originalMap = new LinkedHashMap<>();
        originalMap.put("first_name", "aa");
        originalMap.put("age", "50");

        Map<String, String> originalMap1 = new LinkedHashMap<>();
        originalMap1.put("first_name", "");
        originalMap1.put("age", "51");

        Map<String, String> originalMap2 = new LinkedHashMap<>();
        originalMap2.put("first_name", "aa");
        originalMap2.put("age", "52");

        List<Map<String, String>> originalData = new ArrayList<>();
        originalData.add(originalMap);
        originalData.add(originalMap1);
        originalData.add(originalMap2);

        Properties properties = mock(CustomProperties.class);
        when(properties.getProperty("columnType")).thenReturn("text");
        when(properties.getProperty("columnToFilter")).thenReturn("first_name");
        when(properties.getProperty("condition")).thenReturn("empty");
        when(properties.getProperty("value")).thenReturn("aa");

        // When:
        List<Map<String, String>> modifiedData =
                operation.performOperation(originalData, properties);

        // Then:
        assertThat(modifiedData).isNotNull();
        assertThat(modifiedData.size()).isEqualTo(1);

        Map<String, String> modifiedMap = modifiedData.get(0);
        assertThat(modifiedMap.size()).isEqualTo(2);
        assertThat(modifiedMap.keySet()).containsExactly("first_name", "age");
        assertThat(modifiedMap.values()).containsExactly("", "51");
    }
}
