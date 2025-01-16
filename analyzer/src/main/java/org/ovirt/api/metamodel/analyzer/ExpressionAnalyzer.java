/*
 * Copyright oVirt Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.ovirt.api.metamodel.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ovirt.api.metamodel.concepts.Expression;
import org.ovirt.api.metamodel.concepts.Name;

/**
 * This class is responsible for analyzing the expressions used in the model language for default values and for
 * constraints.
 */
public class ExpressionAnalyzer {

    /**
     * This map contains keywords which should be handled differently
     * while analyzing expressions.
     */
    private static Map<String, Name> keywords = new HashMap<>();
    static {
        //COLLECTION is a keyword used for 'live documentation'.
        //(e.g: mandatory(disk().lunStorage().logicalUnits()[COLLECTION].address());)
        //"COLLECTION" would be broken down to C-O-L-L-E-C-T-I-O-N because all letters
        //are capitals. We don't want that, so we want "collection" returned instead.
        keywords.put("COLLECTION", new Name("Collection"));
    }
    /**
     * Analyzes the given source code and returns the contained expressions. The source may contain multiple
     * expressions, each terminated with a semicolon and optionally preceded by the {@code return} or {@code assert}
     * reserved words.
     *
     * @param source the source code of the constraint
     * @throws IllegalArgumentException if something fails while analyzing the constraint
     */
    public List<Expression> analyzeExpressions(String source) {
        List<Expression> expressions = new ArrayList<>();
        try {
            expressions = TokenProcessor.processTokens(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return expressions;
    }

    /**
     * Analyzes the given source code and returns the contained expression. Only one expression is expected, terminated
     * with a semicolon and preceded by the {@code return} or {@code assert} reserved words.
     *
     * @param source the source code of the constraint
     * @throws IllegalArgumentException if something fails while analyzing the constraint
     */
    public Expression analyzeExpression(String source) {
        List<Expression> expressions = analyzeExpressions(source);
        if (expressions.isEmpty()) {
            throw new IllegalArgumentException(
                "Exactly one expression was expected inside source \"" + source + "\" but none was found"
            );
        }
        if (expressions.size() > 1) {
            throw new IllegalArgumentException(
                "Exactly one expression was expected inside source \"" + source + "\" " +
                "but " + expressions.size() + " were found"
            );
        }
        return expressions.get(0);
    }
}
