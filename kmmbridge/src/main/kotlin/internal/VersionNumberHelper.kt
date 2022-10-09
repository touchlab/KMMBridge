package co.touchlab.faktory.internal

/**
 * Checking Github for the next version. Using kind of a binary search to find it. First we query x*2 till we find an upper
 * bound without a match, then work back with more of a classic binary search.
 * It doesn't do much for the first 10 or so. The 10th version takes 9 checks. Then the big O comes in. The 100th version
 * takes 15 checks, the 1000th takes 21. 500k is still under 40 checks.
 * Github caps api calls to 1000/hour, so you pretty much never have to worry about this.
 */
internal fun findNextVersion(versionPrefix: String, checkExists: (String) -> Boolean): String {

    if (!checkExists("$versionPrefix.0"))
        return "$versionPrefix.0"
    if (!checkExists("$versionPrefix.1"))
        return "$versionPrefix.1"

    // Find a range top beyond published versions. When we find a top that doesn't exist, we can work back with a
    // binary search.
    var rangeFloor = 1
    var rangeTop = 2
    while (checkExists("$versionPrefix.$rangeTop")) {
        rangeFloor = rangeTop
        rangeTop *= 2
    }

    return findNextVersionRecursive(rangeFloor, rangeTop, versionPrefix, checkExists)
}

private fun findNextVersionRecursive(
    rangeFloor: Int,
    rangeTop: Int,
    versionPrefix: String,
    checkExists: (String) -> Boolean
): String {
    if (rangeTop - rangeFloor == 1)
        return "$versionPrefix.$rangeTop"
    val rangePivot = rangeFloor + ((rangeTop - rangeFloor) / 2)
    return if (checkExists("$versionPrefix.$rangePivot")) {
        findNextVersionRecursive(rangePivot, rangeTop, versionPrefix, checkExists)
    } else {
        findNextVersionRecursive(rangeFloor, rangePivot, versionPrefix, checkExists)
    }
}