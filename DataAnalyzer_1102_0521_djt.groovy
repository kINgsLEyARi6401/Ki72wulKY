// 代码生成时间: 2025-11-02 05:21:54
class DataAnalyzer {

    /**
     * Analyze the data provided and return the statistics.
     *
     * @param data The list of data points to analyze.
     * @return A Map containing the analysis results.
     */
    Map<String, Object> analyzeData(List<Double> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Data list is empty or null.")
        }

        Map<String, Object> results = [
            'mean'       : 0.0,
            'median'     : 0.0,
            'stdDev'     : 0.0,
            'max'        : Double.MIN_VALUE,
            'min'        : Double.MAX_VALUE,
            'totalCount' : 0
        ]

        double sum = 0.0
        int count = 0

        // Calculate sum and count for mean
        for (Double value : data) {
            sum += value
            count++
            results['max'] = Math.max(results['max'], value)
            results['min'] = Math.min(results['min'], value)
        }

        results['totalCount'] = count
        results['mean'] = sum / count

        // Sort data for median calculation
        List<Double> sortedData = new ArrayList<>(data)
        Collections.sort(sortedData)

        // Calculate median
        if (count % 2 == 0) {
            results['median'] = (sortedData[count / 2 - 1] + sortedData[count / 2]) / 2
        } else {
            results['median'] = sortedData[count / 2]
        }

        // Calculate standard deviation
        double variance = 0.0
        for (Double value : data) {
            variance += Math.pow(value - results['mean'], 2)
        }
        variance /= count
        results['stdDev'] = Math.sqrt(variance)

        return results
    }

    /**
     * Main method to test the DataAnalyzer class.
     *
     * @param args Command line arguments.
     */
    static void main(String[] args) {
        DataAnalyzer analyzer = new DataAnalyzer()
        List<Double> data = [1.0, 2.0, 3.0, 4.0, 5.0]
        try {
            Map<String, Object> results = analyzer.analyzeData(data)
            println "Analysis Results: $results"
        } catch (IllegalArgumentException e) {
            println "Error: ${e.message}"
        }
    }
}
