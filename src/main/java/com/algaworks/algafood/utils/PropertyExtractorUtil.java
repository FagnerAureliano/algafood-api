package com.algaworks.algafood.utils;

import com.algaworks.algafood.error.models.BusinessException;
import com.algaworks.algafood.error.models.InexistentFieldException;
import java.lang.reflect.Field;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertyExtractorUtil {

    @Autowired
    MessageUtil messageUtil0;

    private static MessageUtil messageUtil;

    public static void setMessageUtil(MessageUtil messageUtil) {
        PropertyExtractorUtil.messageUtil = messageUtil;
    }

    @PostConstruct
    private void initStaticMessageUtil() {
        PropertyExtractorUtil.setMessageUtil(messageUtil0);
    }

    public static Map<String, Object> extract(Object item, List<String> fields) {
        try {
            Map<String, Object> map = new HashMap<>();
            for (String fieldName : fields) {
                fieldName = fieldName.trim();
                if (fieldName.length() == 0) continue;

                List<String> deepFields = Arrays.asList(fieldName.split("\\."));

                Object currentObj = item;
                Map<String, Object> currentMap = map;

                int index = 0;

                for (String deepField : deepFields) {
                    if (currentObj == null) {
                        continue;
                    }

                    Field field = currentObj.getClass().getDeclaredField(deepField);
                    field.setAccessible(true);
                    Object value = field.get(currentObj);

                    if (index == deepFields.size() - 1) {
                        currentMap.put(deepField, value);
                    } else {
                        if (currentMap.get(deepField) != null) {
                            currentMap = (Map<String, Object>) currentMap.get(deepField);
                        } else {
                            Map<String, Object> deeperMap = new HashMap<>();
                            currentMap.put(deepField, deeperMap);
                            currentMap = deeperMap;
                        }

                        currentObj = value;
                    }

                    index++;
                }
            }
            return map;
        } catch (NoSuchFieldException error) {
            throw new InexistentFieldException(messageUtil.getMessage("class.field.nonexistent", error.getLocalizedMessage()));
        } catch (IllegalAccessException error) {
            throw new BusinessException(messageUtil.getMessage("class.field.private"));
        } catch (ArrayIndexOutOfBoundsException error) {
            throw new InexistentFieldException(messageUtil.getMessage("class.field.invalid"));
        }
    }

    public static Object extractValue(Object item, String fieldName)
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        List<String> deepFields = Arrays.asList(fieldName.split("\\."));

        int index = 0;

        Object currentValue;
        Object currentItem = item;

        for (String deepField : deepFields) {
            if (currentItem instanceof List) {
                List<Object> emptyList = new ArrayList<>();

                if (((List<Object>) currentItem).size() == 0) {
                    return emptyList;
                }

                Field field = ((List<Object>) currentItem).get(0).getClass().getDeclaredField(deepField);
                field.setAccessible(true);
                currentValue =
                        ((List<?>) currentItem).stream()
                                .map(
                                        value -> {
                                            try {
                                                return field.get(value);
                                            } catch (IllegalArgumentException e) {
                                                return emptyList;
                                            } catch (IllegalAccessException e) {
                                                return emptyList;
                                            }
                                        }
                                )
                                .collect(Collectors.toList());
            } else {
                Field field = currentItem.getClass().getDeclaredField(deepField);
                field.setAccessible(true);
                currentValue = field.get(currentItem);
            }

            if (currentValue == null) {
                return null;
            }

            if (index == deepFields.size() - 1) {
                return currentValue;
            } else {
                currentItem = currentValue;
            }

            index++;
        }

        return null;
    }
}
