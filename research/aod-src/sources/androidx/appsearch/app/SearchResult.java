package androidx.appsearch.app;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.OptIn;
import androidx.appsearch.flags.FlaggedApi;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.GenericDocumentParcel;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.appsearch.util.BundleUtil;
import androidx.collection.ArrayMap;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@SafeParcelable.Class
public final class SearchResult extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.SearchResultCreator();
    public final String mDatabaseName;
    public final GenericDocumentParcel mDocument;
    public GenericDocument mDocumentCached;
    public final List mInformationalRankingSignals;
    public final List mJoinedResults;
    public final List mMatchInfos;
    public List mMatchInfosCached;
    public final String mPackageName;
    public final Bundle mParentTypeMap;
    public final double mRankingSignal;

    public static final class Builder {
        public boolean mBuilt;
        public final String mDatabaseName;
        public GenericDocument mGenericDocument;
        public List mInformationalRankingSignals;
        public List mJoinedResults;
        public List mMatchInfos;
        public final String mPackageName;
        public Bundle mParentTypeMap;
        public double mRankingSignal;

        public Builder(SearchResult searchResult) {
            this.mMatchInfos = new ArrayList();
            this.mInformationalRankingSignals = new ArrayList();
            this.mParentTypeMap = new Bundle();
            this.mJoinedResults = new ArrayList();
            this.mBuilt = false;
            Preconditions.checkNotNull(searchResult);
            this.mPackageName = searchResult.getPackageName();
            this.mDatabaseName = searchResult.getDatabaseName();
            this.mGenericDocument = searchResult.getGenericDocument();
            this.mRankingSignal = searchResult.getRankingSignal();
            this.mInformationalRankingSignals = new ArrayList(searchResult.getInformationalRankingSignals());
            setParentTypeMap(searchResult.getParentTypeMap());
            List matchInfos = searchResult.getMatchInfos();
            for (int i = 0; i < matchInfos.size(); i++) {
                addMatchInfo(new MatchInfo.Builder((MatchInfo) matchInfos.get(i)).build());
            }
            List joinedResults = searchResult.getJoinedResults();
            for (int i2 = 0; i2 < joinedResults.size(); i2++) {
                addJoinedResult((SearchResult) joinedResults.get(i2));
            }
        }

        public Builder(String str, String str2) {
            this.mMatchInfos = new ArrayList();
            this.mInformationalRankingSignals = new ArrayList();
            this.mParentTypeMap = new Bundle();
            this.mJoinedResults = new ArrayList();
            this.mBuilt = false;
            this.mPackageName = (String) Preconditions.checkNotNull(str);
            this.mDatabaseName = (String) Preconditions.checkNotNull(str2);
        }

        public Builder addInformationalRankingSignal(double d) {
            resetIfBuilt();
            this.mInformationalRankingSignals.add(Double.valueOf(d));
            return this;
        }

        public Builder addJoinedResult(SearchResult searchResult) {
            resetIfBuilt();
            this.mJoinedResults.add(searchResult);
            return this;
        }

        public Builder addMatchInfo(MatchInfo matchInfo) {
            Preconditions.checkState(matchInfo.mDocument == null, "This MatchInfo is already associated with a SearchResult and can't be reassigned");
            resetIfBuilt();
            this.mMatchInfos.add(matchInfo);
            return this;
        }

        public SearchResult build() {
            this.mBuilt = true;
            return new SearchResult(this.mGenericDocument.getDocumentParcel(), this.mMatchInfos, this.mPackageName, this.mDatabaseName, this.mRankingSignal, this.mJoinedResults, this.mInformationalRankingSignals, this.mParentTypeMap);
        }

        public Builder clearMatchInfos() {
            resetIfBuilt();
            this.mMatchInfos.clear();
            return this;
        }

        public final void resetIfBuilt() {
            if (this.mBuilt) {
                this.mMatchInfos = new ArrayList(this.mMatchInfos);
                this.mJoinedResults = new ArrayList(this.mJoinedResults);
                this.mInformationalRankingSignals = new ArrayList(this.mInformationalRankingSignals);
                this.mParentTypeMap = BundleUtil.deepCopy(this.mParentTypeMap);
                this.mBuilt = false;
            }
        }

        public Builder setGenericDocument(GenericDocument genericDocument) {
            Preconditions.checkNotNull(genericDocument);
            resetIfBuilt();
            this.mGenericDocument = genericDocument;
            return this;
        }

        public Builder setParentTypeMap(Map map) {
            Preconditions.checkNotNull(map);
            resetIfBuilt();
            this.mParentTypeMap.clear();
            for (Map.Entry entry : map.entrySet()) {
                Preconditions.checkNotNull((String) entry.getKey());
                Preconditions.checkNotNull((List) entry.getValue());
                ArrayList<String> arrayList = new ArrayList<>(((List) entry.getValue()).size());
                for (int i = 0; i < ((List) entry.getValue()).size(); i++) {
                    arrayList.add((String) Preconditions.checkNotNull((String) ((List) entry.getValue()).get(i)));
                }
                this.mParentTypeMap.putStringArrayList((String) entry.getKey(), arrayList);
            }
            return this;
        }

        public Builder setRankingSignal(double d) {
            resetIfBuilt();
            this.mRankingSignal = d;
            return this;
        }
    }

    @ExperimentalAppSearchApi
    @FlaggedApi
    @SafeParcelable.Class
    public static final class EmbeddingMatchInfo extends AbstractSafeParcelable {
        public static final Parcelable.Creator CREATOR = new StubCreators.EmbeddingMatchInfoCreator();
    }

    @OptIn
    @SafeParcelable.Class
    public static final class MatchInfo extends AbstractSafeParcelable {
        public static final Parcelable.Creator CREATOR = new StubCreators.MatchInfoCreator();
        public final EmbeddingMatchInfo mEmbeddingMatch;
        public final int mExactMatchRangeEnd;
        public final int mExactMatchRangeStart;
        public final String mPropertyPath;
        public final int mSnippetRangeEnd;
        public final int mSnippetRangeStart;
        public final int mSubmatchRangeEnd;
        public final int mSubmatchRangeStart;
        public final TextMatchInfo mTextMatch;
        public PropertyPath mPropertyPathObject = null;
        public GenericDocument mDocument = null;

        public static final class Builder {
            public EmbeddingMatchInfo mEmbeddingMatch;
            public MatchRange mExactMatchRange;
            public final String mPropertyPath;
            public MatchRange mSnippetRange;
            public MatchRange mSubmatchRange;

            public Builder(MatchInfo matchInfo) {
                this.mEmbeddingMatch = null;
                this.mExactMatchRange = new MatchRange(0, 0);
                this.mSubmatchRange = new MatchRange(-1, -1);
                this.mSnippetRange = new MatchRange(0, 0);
                Preconditions.checkNotNull(matchInfo);
                this.mPropertyPath = matchInfo.mPropertyPath;
                this.mEmbeddingMatch = matchInfo.getEmbeddingMatch();
                this.mExactMatchRange = matchInfo.getExactMatchRange();
                this.mSubmatchRange = new MatchRange(matchInfo.mSubmatchRangeStart, matchInfo.mSubmatchRangeEnd);
                this.mSnippetRange = matchInfo.getSnippetRange();
            }

            public Builder(String str) {
                this.mEmbeddingMatch = null;
                this.mExactMatchRange = new MatchRange(0, 0);
                this.mSubmatchRange = new MatchRange(-1, -1);
                this.mSnippetRange = new MatchRange(0, 0);
                this.mPropertyPath = (String) Preconditions.checkNotNull(str);
            }

            public MatchInfo build() {
                TextMatchInfo textMatchInfo;
                if (this.mEmbeddingMatch == null) {
                    textMatchInfo = new TextMatchInfo(this.mExactMatchRange.getStart(), this.mExactMatchRange.getEnd(), this.mSubmatchRange.getStart(), this.mSubmatchRange.getEnd(), this.mSnippetRange.getStart(), this.mSnippetRange.getEnd());
                    textMatchInfo.setPropertyPath(this.mPropertyPath);
                } else {
                    textMatchInfo = null;
                }
                return new MatchInfo(this.mPropertyPath, this.mExactMatchRange.getStart(), this.mExactMatchRange.getEnd(), this.mSubmatchRange.getStart(), this.mSubmatchRange.getEnd(), this.mSnippetRange.getStart(), this.mSnippetRange.getEnd(), textMatchInfo, this.mEmbeddingMatch);
            }

            public Builder setExactMatchRange(MatchRange matchRange) {
                this.mExactMatchRange = (MatchRange) Preconditions.checkNotNull(matchRange);
                return this;
            }

            public Builder setSnippetRange(MatchRange matchRange) {
                this.mSnippetRange = (MatchRange) Preconditions.checkNotNull(matchRange);
                return this;
            }

            public Builder setSubmatchRange(MatchRange matchRange) {
                this.mSubmatchRange = (MatchRange) Preconditions.checkNotNull(matchRange);
                return this;
            }
        }

        public MatchInfo(String str, int i, int i2, int i3, int i4, int i5, int i6, TextMatchInfo textMatchInfo, EmbeddingMatchInfo embeddingMatchInfo) {
            String str2 = (String) Preconditions.checkNotNull(str);
            this.mPropertyPath = str2;
            this.mExactMatchRangeStart = i;
            this.mExactMatchRangeEnd = i2;
            this.mSubmatchRangeStart = i3;
            this.mSubmatchRangeEnd = i4;
            this.mSnippetRangeStart = i5;
            this.mSnippetRangeEnd = i6;
            this.mEmbeddingMatch = embeddingMatchInfo;
            if (textMatchInfo == null && embeddingMatchInfo == null) {
                TextMatchInfo textMatchInfo2 = new TextMatchInfo(i, i2, i3, i4, i5, i6);
                textMatchInfo2.setPropertyPath(str2);
                textMatchInfo = textMatchInfo2;
            }
            this.mTextMatch = textMatchInfo;
        }

        public EmbeddingMatchInfo getEmbeddingMatch() {
            return this.mEmbeddingMatch;
        }

        public MatchRange getExactMatchRange() {
            TextMatchInfo textMatchInfo = this.mTextMatch;
            return textMatchInfo == null ? new MatchRange(0, 0) : textMatchInfo.getExactMatchRange();
        }

        public String getPropertyPath() {
            return this.mPropertyPath;
        }

        public MatchRange getSnippetRange() {
            TextMatchInfo textMatchInfo = this.mTextMatch;
            return textMatchInfo == null ? new MatchRange(0, 0) : textMatchInfo.getSnippetRange();
        }

        public TextMatchInfo getTextMatch() {
            return this.mTextMatch;
        }

        public void setDocument(GenericDocument genericDocument) {
            this.mDocument = (GenericDocument) Preconditions.checkNotNull(genericDocument);
            TextMatchInfo textMatchInfo = this.mTextMatch;
            if (textMatchInfo != null) {
                textMatchInfo.setDocument(genericDocument);
            }
        }
    }

    public static final class MatchRange {
        public final int mEnd;
        public final int mStart;

        public MatchRange(int i, int i2) {
            if (i > i2) {
                throw new IllegalArgumentException("Start point must be less than or equal to end point");
            }
            this.mStart = i;
            this.mEnd = i2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MatchRange)) {
                return false;
            }
            MatchRange matchRange = (MatchRange) obj;
            return getStart() == matchRange.getStart() && getEnd() == matchRange.getEnd();
        }

        public int getEnd() {
            return this.mEnd;
        }

        public int getStart() {
            return this.mStart;
        }

        public int hashCode() {
            return ObjectsCompat.hash(Integer.valueOf(this.mStart), Integer.valueOf(this.mEnd));
        }

        public String toString() {
            return "MatchRange { start: " + this.mStart + " , end: " + this.mEnd + "}";
        }
    }

    @ExperimentalAppSearchApi
    @FlaggedApi
    @SafeParcelable.Class
    public static final class TextMatchInfo extends AbstractSafeParcelable {
        public static final Parcelable.Creator CREATOR = new StubCreators.TextMatchInfoCreator();
        public MatchRange mExactMatchRangeCached;
        public final int mExactMatchRangeEnd;
        public final int mExactMatchRangeStart;
        public final int mSnippetRangeEnd;
        public final int mSnippetRangeStart;
        public final int mSubmatchRangeEnd;
        public final int mSubmatchRangeStart;
        public MatchRange mWindowRangeCached;
        public String mPropertyPath = null;
        public GenericDocument mDocument = null;

        public TextMatchInfo(int i, int i2, int i3, int i4, int i5, int i6) {
            this.mExactMatchRangeStart = i;
            this.mExactMatchRangeEnd = i2;
            this.mSubmatchRangeStart = i3;
            this.mSubmatchRangeEnd = i4;
            this.mSnippetRangeStart = i5;
            this.mSnippetRangeEnd = i6;
        }

        public MatchRange getExactMatchRange() {
            if (this.mExactMatchRangeCached == null) {
                this.mExactMatchRangeCached = new MatchRange(this.mExactMatchRangeStart, this.mExactMatchRangeEnd);
            }
            return this.mExactMatchRangeCached;
        }

        public MatchRange getSnippetRange() {
            if (this.mWindowRangeCached == null) {
                this.mWindowRangeCached = new MatchRange(this.mSnippetRangeStart, this.mSnippetRangeEnd);
            }
            return this.mWindowRangeCached;
        }

        public void setDocument(GenericDocument genericDocument) {
            this.mDocument = (GenericDocument) Preconditions.checkNotNull(genericDocument);
        }

        public void setPropertyPath(String str) {
            this.mPropertyPath = (String) Preconditions.checkNotNull(str);
        }
    }

    public SearchResult(GenericDocumentParcel genericDocumentParcel, List list, String str, String str2, double d, List list2, List list3, Bundle bundle) {
        this.mDocument = (GenericDocumentParcel) Preconditions.checkNotNull(genericDocumentParcel);
        this.mMatchInfos = (List) Preconditions.checkNotNull(list);
        this.mPackageName = (String) Preconditions.checkNotNull(str);
        this.mDatabaseName = (String) Preconditions.checkNotNull(str2);
        this.mRankingSignal = d;
        this.mJoinedResults = Collections.unmodifiableList((List) Preconditions.checkNotNull(list2));
        if (list3 != null) {
            this.mInformationalRankingSignals = Collections.unmodifiableList(list3);
        } else {
            this.mInformationalRankingSignals = Collections.EMPTY_LIST;
        }
        if (bundle != null) {
            this.mParentTypeMap = bundle;
        } else {
            this.mParentTypeMap = Bundle.EMPTY;
        }
    }

    public String getDatabaseName() {
        return this.mDatabaseName;
    }

    public GenericDocument getGenericDocument() {
        if (this.mDocumentCached == null) {
            this.mDocumentCached = new GenericDocument(this.mDocument);
        }
        return this.mDocumentCached;
    }

    public List getInformationalRankingSignals() {
        return this.mInformationalRankingSignals;
    }

    public List getJoinedResults() {
        return this.mJoinedResults;
    }

    public List getMatchInfos() {
        if (this.mMatchInfosCached == null) {
            this.mMatchInfosCached = new ArrayList(this.mMatchInfos.size());
            for (int i = 0; i < this.mMatchInfos.size(); i++) {
                MatchInfo matchInfo = (MatchInfo) this.mMatchInfos.get(i);
                matchInfo.setDocument(getGenericDocument());
                if (matchInfo.getTextMatch() != null) {
                    matchInfo.mTextMatch.setPropertyPath(matchInfo.getPropertyPath());
                }
                List list = this.mMatchInfosCached;
                if (list != null) {
                    list.add(matchInfo);
                }
            }
            this.mMatchInfosCached = Collections.unmodifiableList(this.mMatchInfosCached);
        }
        return (List) Preconditions.checkNotNull(this.mMatchInfosCached);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public Map getParentTypeMap() {
        Set<String> setKeySet = this.mParentTypeMap.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            ArrayList<String> stringArrayList = this.mParentTypeMap.getStringArrayList(str);
            if (stringArrayList != null) {
                arrayMap.put(str, stringArrayList);
            }
        }
        return arrayMap;
    }

    public double getRankingSignal() {
        return this.mRankingSignal;
    }
}
