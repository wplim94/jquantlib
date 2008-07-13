/*
 Copyright (C) 2007 Richard Gomes

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquant-devel@lists.sourceforge.net>. The license is also available online at
 <http://www.jquantlib.org/index.php/LICENSE.TXT>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
 
 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */

/*
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006 StatPro Italia srl
 Copyright (C) 2004 Ferdinando Ametrano

 This file is part of QuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://quantlib.org/

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
*/

package org.jquantlib.math;

/**
 * 1-D array used in linear algebra.
 * <p>
 * This class implements the concept of vector as used in linear algebra.
 * As such, it is <b>not</b> meant to be used as a container java.util.List should be used instead.
 * 
 * //  TODO: Discussion Points
 *    I generally disagree with the O-O philosophy of 
 *    creating a class that both has operations, and data for things like this.
 *    However, That philosophy will stay in tack here until further notice.
 * 
 * @author Richard Gomes
 * @author Q.Boiler
 */
//TEST construction of arrays is checked in a number of cases
public class Array {
	double[] data;
	int size;

	/**  This will be used to load linear algabra package that,
	 *   JQuantLib Uses.   However, most of the operations will
	 *   not respect this variable, so use of this should merely 
	 *   be viewed as a suggestion.
	 */
	public static enum LAPACK_MODE 
	        {PARALLEL_COLT, COLT, FAST_UTIL, LOCAL_CALC, JAL};
	//  Huge Assumption,  Colt Algebra class is thread safe.
        private static cern.colt.matrix.linalg.Algebra algebra = new cern.colt.matrix.linalg.Algebra();


	//----------------------------------------------------------------------
	//  
	//  Constructors
	//
	//----------------------------------------------------------------------

        public Array(int s){

		this.data = new double[s];
		this.size = s;

	}

        public Array(int s, double value){
		this(s);
		for(int i=0;i<s;++i){
			data[i]=value;
		}
	}

      /**! brief creates the array and fills it according to
        * \f$ a_{0} = value, a_{i}=a_{i-1}+increment \f$
      */
      public Array(int s, double value, double increment){
	      this(s);
	      for(int i=0;i<size;++i){
		      data[i]=i*increment;
	      }
      }
	//----------------------------------------------------------------------
	//  END Constructors
	//----------------------------------------------------------------------


	
	/**  Calulates the dotProduct of vectorA, and vectorB.
	 *   @throws Exception if vectorA or vectorB is null, or if vectorA.length
	 *    is not equal to vectorB.length.  
	 */
        public static double dotProduct(final Array vectorA, final Array vectorB) throws Exception{
		return dotProduct(vectorA.data,vectorB.data);
	}

	/** 
	 * Calculates the dotProduct of vectorA and vectorB, No null checks or 
	 * bounds checks are performed
	 * @param vectorA has the precondition that it is a non-null Array the same size as vectorB
	 * @param vectorB has the precondition that it is a non-null Array the same size as vectorA
	 * @return the dotProduct/InnerProduct of vectorA and vectorB.
	 */
	public static double quickDotProduct(final Array vectorA, final Array vectorB){
		//  This will only throw un-checked exceptions.
		return quickDotProduct(vectorA.data,vectorB.data);
	}

	/**  Calulates the dotProduct of vectorA, and vectorB.
	 *   @throws Exception if vectorA or vectorB is null, or if vectorA.length
	 *    is not equal to vectorB.length.  
	 */
        public static double dotProduct(final double[] vectorA, final double[] vectorB) throws Exception{
		
		//  Done as a local calc.
		if(vectorA != null && vectorB !=null && vectorA.length==vectorB.length){
			return quickDotProduct(vectorA,vectorB);
		}else{
			//  TODO make this a JQuantLib Specific Checked Exception.
			throw new Exception("VectorA and VectorB must both be non-null and the same length.");
		}

	}


	/** 
	 * Calculates the dotProduct of vectorA and vectorB, No null checks or 
	 * bounds checks are performed
	 * @param vectorA has the precondition that it is a non-null double[] the same size as vectorB
	 * @param vectorB has the precondition that it is a non-null double[] the same size as vectorA
	 * @return the dotProduct/InnerProduct of vectorA and vectorB.
	 */
	public static double quickDotProduct(final double[] vectorA, final double[] vectorB){
		//  This will only throw un-checked exceptions.
		double result = 0.0d;
		for(int i=0;i<vectorA.length;++i){
			result+=vectorA[i]*vectorB[i];
		}
		return result;
	}

	/**
	 * If both arrays are null true will be returned, this may be confusing.
	 * if both are identical false is returned.
	 * @param paramArray
	 * @return
	 */
        public boolean operatorNotEquals(final Array paramArray) {
		return !operatorEquals(paramArray);
	}

        public boolean operatorEquals(final Array paramArray) {
		if(this.data==null  || paramArray == null || paramArray.data ==null){
			return false;
		}else if(data.length!=paramArray.data.length){
			return false;
		}else{
			return operatorEquals(paramArray.data);
		}
	}
	/**
	 * returns true or false or throws an exception.
	 * NO BOUNDS CHECKING OR NULL CHECKING is done here.
	 * @param paramData
	 * @return
	 */
	public boolean operatorEquals(final double[] paramData){
		for(int i=0;i<data.length;++i){
			if(data[i]!=paramData[i]){
				return false;
			}
		}
		return true;
	}



}


//class Array {
//    public:
//      //! \name Constructors, destructor, and assignment
//      //@{
//      //! creates the array with the given dimension
//      explicit Array(Size size = 0);
//      //! creates the array and fills it with <tt>value</tt>
//      Array(Size size, Real value);
//      /*! \brief creates the array and fills it according to
//          \f$ a_{0} = value, a_{i}=a_{i-1}+increment \f$
//      */
//      Array(Size size, Real value, Real increment);
//      Array(const Array&);
//      Array(const Disposable<Array>&);
//      //! creates the array as a copy of a given stl vector
//      explicit Array(const std::vector<Real>&);
//
//      Array& operator=(const Array&);
//      Array& operator=(const Disposable<Array>&);
//      bool operator==(const Array&) const;
//      bool operator!=(const Array&) const;
//      //@}
//      /*! \name Vector algebra
//
//          <tt>v += x</tt> and similar operation involving a scalar value
//          are shortcuts for \f$ \forall i : v_i = v_i + x \f$
//
//          <tt>v *= w</tt> and similar operation involving two vectors are
//          shortcuts for \f$ \forall i : v_i = v_i \times w_i \f$
//
//          \pre all arrays involved in an algebraic expression must have
//          the same size.
//      */
//      //@{
//      const Array& operator+=(const Array&);
//      const Array& operator+=(Real);
//      const Array& operator-=(const Array&);
//      const Array& operator-=(Real);
//      const Array& operator*=(const Array&);
//      const Array& operator*=(Real);
//      const Array& operator/=(const Array&);
//      const Array& operator/=(Real);
//      //@}
//      //! \name Element access
//      //@{
//      //! read-only
//      Real operator[](Size) const;
//      Real at(Size) const;
//      Real front() const;
//      Real back() const;
//      //! read-write
//      Real& operator[](Size);
//      Real& at(Size);
//      Real& front();
//      Real& back();
//      //@}
//      //! \name Inspectors
//      //@{
//      //! dimension of the array
//      Size size() const;
//      //! whether the array is empty
//      bool empty() const;
//      //@}
//      typedef Real value_type;
//      typedef Real* iterator;
//      typedef const Real* const_iterator;
//      typedef boost::reverse_iterator<iterator> reverse_iterator;
//      typedef boost::reverse_iterator<const_iterator> const_reverse_iterator;
//      //! \name Iterator access
//      //@{
//      const_iterator begin() const;
//      iterator begin();
//      const_iterator end() const;
//      iterator end();
//      const_reverse_iterator rbegin() const;
//      reverse_iterator rbegin();
//      const_reverse_iterator rend() const;
//      reverse_iterator rend();
//      //@}
//      //! \name Utilities
//      //@{
//      void swap(Array&);  // never throws
//      //@}
//
//    private:
//      boost::scoped_array<Real> data_;
//      Size n_;
//  };
//
//  /*! \relates Array */
//  Real DotProduct(const Array&, const Array&);
//
//  // unary operators
//  /*! \relates Array */
//  const Disposable<Array> operator+(const Array& v);
//  /*! \relates Array */
//  const Disposable<Array> operator-(const Array& v);
//
//  // binary operators
//  /*! \relates Array */
//  const Disposable<Array> operator+(const Array&, const Array&);
//  /*! \relates Array */
//  const Disposable<Array> operator+(const Array&, Real);
//  /*! \relates Array */
//  const Disposable<Array> operator+(Real, const Array&);
//  /*! \relates Array */
//  const Disposable<Array> operator-(const Array&, const Array&);
//  /*! \relates Array */
//  const Disposable<Array> operator-(const Array&, Real);
//  /*! \relates Array */
//  const Disposable<Array> operator-(Real, const Array&);
//  /*! \relates Array */
//  const Disposable<Array> operator*(const Array&, const Array&);
//  /*! \relates Array */
//  const Disposable<Array> operator*(const Array&, Real);
//  /*! \relates Array */
//  const Disposable<Array> operator*(Real, const Array&);
//  /*! \relates Array */
//  const Disposable<Array> operator/(const Array&, const Array&);
//  /*! \relates Array */
//  const Disposable<Array> operator/(const Array&, Real);
//  /*! \relates Array */
//  const Disposable<Array> operator/(Real, const Array&);
//
//  // math functions
//  /*! \relates Array */
//  const Disposable<Array> Abs(const Array&);
//  /*! \relates Array */
//  const Disposable<Array> Sqrt(const Array&);
//  /*! \relates Array */
//  const Disposable<Array> Log(const Array&);
//  /*! \relates Array */
//  const Disposable<Array> Exp(const Array&);
//
//  // utilities
//  /*! \relates Array */
//  void swap(Array&, Array&);
//
//  // format
//  /*! \relates Array */
//  std::ostream& operator<<(std::ostream&, const Array&);
//
//
//  // inline definitions
//
//  inline Array::Array(Size size)
//  : data_(size ? new Real[size] : (Real*)(0)), n_(size) {}
//
//  inline Array::Array(Size size, Real value)
//  : data_(size ? new Real[size] : (Real*)(0)), n_(size) {
//      std::fill(begin(),end(),value);
//  }
//
//  inline Array::Array(Size size, Real value, Real increment)
//  : data_(size ? new Real[size] : (Real*)(0)), n_(size) {
//      for (iterator i=begin(); i!=end(); i++,value+=increment)
//          *i = value;
//  }
//
//  inline Array::Array(const Array& from)
//  : data_(from.n_ ? new Real[from.n_] : (Real*)(0)), n_(from.n_) {
//      std::copy(from.begin(),from.end(),begin());
//  }
//
//  inline Array::Array(const Disposable<Array>& from)
//  : data_((Real*)(0)), n_(0) {
//      swap(const_cast<Disposable<Array>&>(from));
//  }
//
//  inline Array::Array(const std::vector<Real>& from)
//  : data_(from.size() ? new Real[from.size()] : (Real*)(0)), 
//    n_(from.size()) {
//      std::copy(from.begin(),from.end(),begin());
//  }
//
//  inline Array& Array::operator=(const Array& from) {
//      // strong guarantee
//      Array temp(from);
//      swap(temp);
//      return *this;
//  }
//
//  inline bool Array::operator==(const Array& to) const {
//      return (n_ == to.n_) && std::equal(begin(), end(), to.begin());
//  }
//
//  inline bool Array::operator!=(const Array& to) const {
//      return !(this->operator==(to));
//  }
//
//  inline Array& Array::operator=(const Disposable<Array>& from) {
//      swap(const_cast<Disposable<Array>&>(from));
//      return *this;
//  }
//
//  inline const Array& Array::operator+=(const Array& v) {
//      QL_REQUIRE(n_ == v.n_,
//                 "arrays with different sizes (" << n_ << ", "
//                 << v.n_ << ") cannot be added");
//      std::transform(begin(),end(),v.begin(),begin(),
//                     std::plus<Real>());
//      return *this;
//  }
//
//
//  inline const Array& Array::operator+=(Real x) {
//      std::transform(begin(),end(),begin(),
//                     std::bind2nd(std::plus<Real>(),x));
//      return *this;
//  }
//
//  inline const Array& Array::operator-=(const Array& v) {
//      QL_REQUIRE(n_ == v.n_,
//                 "arrays with different sizes (" << n_ << ", "
//                 << v.n_ << ") cannot be subtracted");
//      std::transform(begin(),end(),v.begin(),begin(),
//                     std::minus<Real>());
//      return *this;
//  }
//
//  inline const Array& Array::operator-=(Real x) {
//      std::transform(begin(),end(),begin(),
//                     std::bind2nd(std::minus<Real>(),x));
//      return *this;
//  }
//
//  inline const Array& Array::operator*=(const Array& v) {
//      QL_REQUIRE(n_ == v.n_,
//                 "arrays with different sizes (" << n_ << ", "
//                 << v.n_ << ") cannot be multiplied");
//      std::transform(begin(),end(),v.begin(),begin(),
//                     std::multiplies<Real>());
//      return *this;
//  }
//
//  inline const Array& Array::operator*=(Real x) {
//      std::transform(begin(),end(),begin(),
//                     std::bind2nd(std::multiplies<Real>(),x));
//      return *this;
//  }
//
//  inline const Array& Array::operator/=(const Array& v) {
//      QL_REQUIRE(n_ == v.n_,
//                 "arrays with different sizes (" << n_ << ", "
//                 << v.n_ << ") cannot be divided");
//      std::transform(begin(),end(),v.begin(),begin(),
//                     std::divides<Real>());
//      return *this;
//  }
//
//  inline const Array& Array::operator/=(Real x) {
//      std::transform(begin(),end(),begin(),
//                     std::bind2nd(std::divides<Real>(),x));
//      return *this;
//  }
//
//  inline Real Array::operator[](Size i) const {
//      #if defined(QL_EXTRA_SAFETY_CHECKS)
//      QL_REQUIRE(i<n_,
//                 "index (" << i << ") must be less than " << n_ <<
//                 ": array access out of range");
//      #endif
//      return data_.get()[i];
//  }
//
//  inline Real Array::at(Size i) const {
//      QL_REQUIRE(i<n_,
//                 "index (" << i << ") must be less than " << n_ <<
//                 ": array access out of range");
//      return data_.get()[i];
//  }
//
//  inline Real Array::front() const {
//      #if defined(QL_EXTRA_SAFETY_CHECKS)
//      QL_REQUIRE(n_>0, "null Array: array access out of range");
//      #endif
//      return data_.get()[0];
//  }
//
//  inline Real Array::back() const {
//      #if defined(QL_EXTRA_SAFETY_CHECKS)
//      QL_REQUIRE(n_>0, "null Array: array access out of range");
//      #endif
//      return data_.get()[n_-1];
//  }
//
//  inline Real& Array::operator[](Size i) {
//      #if defined(QL_EXTRA_SAFETY_CHECKS)
//      QL_REQUIRE(i<n_,
//                 "index (" << i << ") must be less than " << n_ <<
//                 ": array access out of range");
//      #endif
//      return data_.get()[i];
//  }
//
//  inline Real& Array::at(Size i) {
//      QL_REQUIRE(i<n_,
//                 "index (" << i << ") must be less than " << n_ <<
//                 ": array access out of range");
//      return data_.get()[i];
//  }
//
//  inline Real& Array::front() {
//      #if defined(QL_EXTRA_SAFETY_CHECKS)
//      QL_REQUIRE(n_>0, "null Array: array access out of range");
//      #endif
//      return data_.get()[0];
//  }
//
//  inline Real& Array::back() {
//      #if defined(QL_EXTRA_SAFETY_CHECKS)
//      QL_REQUIRE(n_>0, "null Array: array access out of range");
//      #endif
//      return data_.get()[n_-1];
//  }
//
//  inline Size Array::size() const {
//      return n_;
//  }
//
//  inline bool Array::empty() const {
//      return n_ == 0;
//  }
//
//  inline Array::const_iterator Array::begin() const {
//      return data_.get();
//  }
//
//  inline Array::iterator Array::begin() {
//      return data_.get();
//  }
//
//  inline Array::const_iterator Array::end() const {
//      return data_.get()+n_;
//  }
//
//  inline Array::iterator Array::end() {
//      return data_.get()+n_;
//  }
//
//  inline Array::const_reverse_iterator Array::rbegin() const {
//      return const_reverse_iterator(end());
//  }
//
//  inline Array::reverse_iterator Array::rbegin() {
//      return reverse_iterator(end());
//  }
//
//  inline Array::const_reverse_iterator Array::rend() const {
//      return const_reverse_iterator(begin());
//  }
//
//  inline Array::reverse_iterator Array::rend() {
//      return reverse_iterator(begin());
//  }
//
//  inline void Array::swap(Array& from) {
//      using std::swap;
//      data_.swap(from.data_);
//      swap(n_,from.n_);
//  }
//
//  // dot product
//
//  inline Real DotProduct(const Array& v1, const Array& v2) {
//      QL_REQUIRE(v1.size() == v2.size(),
//                 "arrays with different sizes (" << v1.size() << ", "
//                 << v2.size() << ") cannot be multiplied");
//      return std::inner_product(v1.begin(),v1.end(),v2.begin(),0.0);
//  }
//
//  // overloaded operators
//
//  // unary
//
//  inline const Disposable<Array> operator+(const Array& v) {
//      Array result = v;
//      return result;
//  }
//
//  inline const Disposable<Array> operator-(const Array& v) {
//      Array result(v.size());
//      std::transform(v.begin(),v.end(),result.begin(),
//                     std::negate<Real>());
//      return result;
//  }
//
//
//  // binary operators
//
//  inline const Disposable<Array> operator+(const Array& v1,
//                                           const Array& v2) {
//      QL_REQUIRE(v1.size() == v2.size(),
//                 "arrays with different sizes (" << v1.size() << ", "
//                 << v2.size() << ") cannot be added");
//      Array result(v1.size());
//      std::transform(v1.begin(),v1.end(),v2.begin(),result.begin(),
//                     std::plus<Real>());
//      return result;
//  }
//
//  inline const Disposable<Array> operator+(const Array& v1, Real a) {
//      Array result(v1.size());
//      std::transform(v1.begin(),v1.end(),result.begin(),
//                     std::bind2nd(std::plus<Real>(),a));
//      return result;
//  }
//
//  inline const Disposable<Array> operator+(Real a, const Array& v2) {
//      Array result(v2.size());
//      std::transform(v2.begin(),v2.end(),result.begin(),
//                     std::bind1st(std::plus<Real>(),a));
//      return result;
//  }
//
//  inline const Disposable<Array> operator-(const Array& v1,
//                                           const Array& v2) {
//      QL_REQUIRE(v1.size() == v2.size(),
//                 "arrays with different sizes (" << v1.size() << ", "
//                 << v2.size() << ") cannot be subtracted");
//      Array result(v1.size());
//      std::transform(v1.begin(),v1.end(),v2.begin(),result.begin(),
//                     std::minus<Real>());
//      return result;
//  }
//
//  inline const Disposable<Array> operator-(const Array& v1, Real a) {
//      Array result(v1.size());
//      std::transform(v1.begin(),v1.end(),result.begin(),
//                     std::bind2nd(std::minus<Real>(),a));
//      return result;
//  }
//
//  inline const Disposable<Array> operator-(Real a, const Array& v2) {
//      Array result(v2.size());
//      std::transform(v2.begin(),v2.end(),result.begin(),
//                     std::bind1st(std::minus<Real>(),a));
//      return result;
//  }
//
//  inline const Disposable<Array> operator*(const Array& v1,
//                                           const Array& v2) {
//      QL_REQUIRE(v1.size() == v2.size(),
//                 "arrays with different sizes (" << v1.size() << ", "
//                 << v2.size() << ") cannot be multiplied");
//      Array result(v1.size());
//      std::transform(v1.begin(),v1.end(),v2.begin(),result.begin(),
//                     std::multiplies<Real>());
//      return result;
//  }
//
//  inline const Disposable<Array> operator*(const Array& v1, Real a) {
//      Array result(v1.size());
//      std::transform(v1.begin(),v1.end(),result.begin(),
//                     std::bind2nd(std::multiplies<Real>(),a));
//      return result;
//  }
//
//  inline const Disposable<Array> operator*(Real a, const Array& v2) {
//      Array result(v2.size());
//      std::transform(v2.begin(),v2.end(),result.begin(),
//                     std::bind1st(std::multiplies<Real>(),a));
//      return result;
//  }
//
//  inline const Disposable<Array> operator/(const Array& v1,
//                                           const Array& v2) {
//      QL_REQUIRE(v1.size() == v2.size(),
//                 "arrays with different sizes (" << v1.size() << ", "
//                 << v2.size() << ") cannot be divided");
//      Array result(v1.size());
//      std::transform(v1.begin(),v1.end(),v2.begin(),result.begin(),
//                     std::divides<Real>());
//      return result;
//  }
//
//  inline const Disposable<Array> operator/(const Array& v1, Real a) {
//      Array result(v1.size());
//      std::transform(v1.begin(),v1.end(),result.begin(),
//                     std::bind2nd(std::divides<Real>(),a));
//      return result;
//  }
//
//  inline const Disposable<Array> operator/(Real a, const Array& v2) {
//      Array result(v2.size());
//      std::transform(v2.begin(),v2.end(),result.begin(),
//                     std::bind1st(std::divides<Real>(),a));
//      return result;
//  }
//
//  // functions
//
//  inline const Disposable<Array> Abs(const Array& v) {
//      Array result(v.size());
//      std::transform(v.begin(),v.end(),result.begin(),
//                     std::ptr_fun<Real,Real>(std::fabs));
//      return result;
//  }
//
//  inline const Disposable<Array> Sqrt(const Array& v) {
//      Array result(v.size());
//      std::transform(v.begin(),v.end(),result.begin(),
//                     std::ptr_fun<Real,Real>(std::sqrt));
//      return result;
//  }
//
//  inline const Disposable<Array> Log(const Array& v) {
//      Array result(v.size());
//      std::transform(v.begin(),v.end(),result.begin(),
//                     std::ptr_fun<Real,Real>(std::log));
//      return result;
//  }
//
//  inline const Disposable<Array> Exp(const Array& v) {
//      Array result(v.size());
//      std::transform(v.begin(),v.end(),result.begin(),
//                     std::ptr_fun<Real,Real>(std::exp));
//      return result;
//  }
//
//  inline void swap(Array& v, Array& w) {
//      v.swap(w);
//  }
//
//  inline std::ostream& operator<<(std::ostream& out, const Array& a) {
//      std::streamsize width = out.width();
//      out << "[ ";
//      if (!a.empty()) {
//          for (Size n=0; n<a.size()-1; ++n)
//              out << std::setw(width) << a[n] << "; ";
//          out << std::setw(width) << a.back();
//      }
//      out << " ]";
//      return out;
//  }
//
//}
