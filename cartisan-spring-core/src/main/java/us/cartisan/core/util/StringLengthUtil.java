package us.cartisan.core.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author Wonjun Jang
 */
public final class StringLengthUtil {
	public static final String DEFAULT_ELLIPSIS = "...";

	public static String ellipsisByChar(String source, int charSize) {
		return ellipsisByChar(source, charSize, DEFAULT_ELLIPSIS);
	}

	public static String ellipsisByChar(String source, int charSize, String ellipsis) {
		if (StringUtils.isEmpty(source)) {
			return "";
		}
		if (charSize <= 0 || source.length() <= charSize) {
			return source;
		}
		char[] arr = source.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < charSize; i++) {
			sb.append(arr[i]);
		}
		sb.append(StringUtils.defaultString(ellipsis));
		return sb.toString();
	}

	public static String ellipsisByteWise(String source, int byteWiseMaxSize) {
		return ellipsisByteWise(source, byteWiseMaxSize, DEFAULT_ELLIPSIS);
	}

	public static String ellipsisByteWise(String source, int byteWiseMaxSize, String ellipsis) {
		int sourceSize = sizeByteWise(source);
		int ellipsisSize = sizeByteWise(ellipsis);
		if (sourceSize <= byteWiseMaxSize) {
			return source;
		}

		if (ellipsis != null && byteWiseMaxSize < ellipsisSize) {
			throw new IllegalArgumentException("ellipsis too long");
		}

		return cutByteWise(source, byteWiseMaxSize - ellipsisSize) + StringUtils.defaultString(ellipsis);
	}

	public static String cutByteWise(String source, int byteLength) {
		if (source == null || byteLength < 0) {
			return null;
		}

		int currentLength = 0;
		StringBuilder builer = new StringBuilder();
		for (int index = 0; index < source.length(); index++) {
			String charecter = source.substring(index, index + 1);
			if (currentLength + charecter.getBytes().length > byteLength) {
				return builer.toString();
			} else {
				currentLength = currentLength + charecter.getBytes().length;
				builer.append(charecter);
			}

		}

		return builer.toString();
	}

	public static int sizeByteWise(String source) {
		if (source == null) {
			return 0;
		}

		return source.getBytes().length;
	}

	public static String ellipsisForEucKr(String source, int maxSize) {
		return ellipsisForEucKr(source, maxSize, DEFAULT_ELLIPSIS);
	}

	public static String ellipsisForEucKr(String source, int maxSize, String ellipsis) {
		int sourceSize = sizeForEucKr(source);
		int ellipsisSize = sizeForEucKr(ellipsis);
		if (sourceSize <= maxSize) {
			return source;
		}

		if (ellipsis != null && maxSize < ellipsisSize) {
			throw new IllegalArgumentException("ellipsis too long");
		}

		return cutForEucKr(source, maxSize - ellipsisSize) + StringUtils.defaultString(ellipsis);
	}

	public static String cutForEucKr(String source, int max) {
		int currentLength = 0;
		StringBuilder builder = new StringBuilder();
		for (int index = 0; index < source.length(); index++) {
			char ch = source.charAt(index);
			int charSize = 1;
			if (ch > 0xFF) {
				charSize = 2;
			}

			if (charSize + currentLength > max) {
				break;
			} else {
				builder.append(ch);
				currentLength = currentLength + charSize;
			}
		}

		return builder.toString();
	}

	public static int sizeForEucKr(String source) {
		int size = 0;
		for (int index = 0; index < source.length(); index++) {
			char ch = source.charAt(index);
			if (ch > 0xFF) {
				size = size + 2;
			} else {
				size = size + 1;
			}
		}

		return size;
	}

	public static String ellipsis(String source, int maxLength) {
		return ellipsis(source, maxLength, DEFAULT_ELLIPSIS);
	}

	public static String ellipsis(String source, int maxLength, String ellipsis) {
		if (source == null || source.length() <= maxLength) {
			return source;
		}

		if (ellipsis != null && maxLength < ellipsis.length()) {
			throw new IllegalArgumentException("ellipsis too long");
		}

		String postfix = StringUtils.defaultString(ellipsis);

		return source.substring(0, maxLength - postfix.length()) + postfix;
	}

	public static String getScheduleTime(String start, String end) {
		String formatStartEnd = "%s.%s.%s. %s:%s~%s:%s";
		String formatStart = "%s.%s.%s. %s:%s";

		String startYear = StringUtils.substring(start, 0, 4);
		String startMonth = StringUtils.substring(start, 4, 6);
		String startDay = StringUtils.substring(start, 6, 8);
		String startHour = StringUtils.substring(start, 8, 10);
		String startMin = StringUtils.substring(start, 10, 12);

		@SuppressWarnings("unused") String endYear = StringUtils.substring(end, 0, 4);
		@SuppressWarnings("unused") String endMonth = StringUtils.substring(end, 4, 6);
		@SuppressWarnings("unused") String endDay = StringUtils.substring(end, 6, 8);
		String endHour = StringUtils.substring(end, 8, 10);
		String endMin = StringUtils.substring(end, 10, 12);

		if (StringUtils.isEmpty(end)) {
			return String.format(formatStart, startYear, startMonth, startDay, startHour, startMin);
		}
		return String.format(formatStartEnd, startYear, startMonth, startDay, startHour, startMin, endHour, endMin);
	}

	/**
	 * 버전 비교
	 * 
	 * @param original 원본
	 * @param other 비교할 대상
	 * @return 원본이 크면 0 초과, 대상이 크면 0 미만, 같으면 0
	 */
	public static int versionCompare(String original, String other) {
		String s1 = normalisedVersion(original);
		String s2 = normalisedVersion(other);
		return s1.compareTo(s2);
	}

	private static String normalisedVersion(String version) {
		return normalisedVersion(StringUtils.defaultString(version), ".", 10);
	}

	private static String normalisedVersion(String version, String sep, int maxWidth) {
		String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version);
		StringBuilder sb = new StringBuilder();
		for (String s : split) {
			sb.append(String.format("%" + maxWidth + 's', s));
		}
		return sb.toString();
	}

	private StringLengthUtil() {}
}