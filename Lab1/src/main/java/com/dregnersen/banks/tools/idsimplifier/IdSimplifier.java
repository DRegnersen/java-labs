package com.dregnersen.banks.tools.idsimplifier;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The type Id simplifier.
 */
public class IdSimplifier {
    private static final long FIRST_SIMPLE_ID = 0;
    private static final long SIMPLE_ID_STEP = 1;
    private static long currentSimpleId;
    private final Map<Long, UUID> simpleToInitialMap;

    {
        currentSimpleId = FIRST_SIMPLE_ID;
        simpleToInitialMap = new HashMap<>();
    }

    /**
     * Create simple id long.
     *
     * @param id the id
     * @return the long
     */
    public long createSimpleId(UUID id) {
        try {
            currentSimpleId = Math.addExact(currentSimpleId, SIMPLE_ID_STEP);
        } catch (ArithmeticException e) {
            throw new UnsupportedOperationException();
        }

        simpleToInitialMap.put(currentSimpleId, id);
        return currentSimpleId;
    }

    /**
     * Gets initial id.
     *
     * @param id the id
     * @return the initial id
     */
    public UUID getInitialId(long id) {
        if (!simpleToInitialMap.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        return simpleToInitialMap.get(id);
    }
}
