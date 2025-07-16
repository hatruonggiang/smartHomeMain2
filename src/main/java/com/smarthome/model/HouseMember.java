package com.smarthome.model;

import com.smarthome.enums.HouseMemberRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_house_members")
@Data
@NoArgsConstructor
public class HouseMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HouseMemberRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invited_by")
    private User invitedBy;

    @CreationTimestamp
    private LocalDateTime joinedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Getter and Setter

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public House getHouse() {
        return house;
    }

    public HouseMemberRole getRole() {
        return role;
    }

    public User getInvitedBy() {
        return invitedBy;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setRole(HouseMemberRole role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public void setInvitedBy(User invitedBy) {
        this.invitedBy = invitedBy;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /** Hàm private nhận Builder—chỉ Builder mới gọi được. */
    private HouseMember(Builder builder) {
        this.house      = builder.house;
        this.user       = builder.user;
        this.role       = builder.role;
        this.invitedBy  = builder.invitedBy;
        this.joinedAt  = builder.joinedAt;
        this.updatedAt  = builder.updatedAt;
    }

    /** Điểm khởi tạo builder. */
    public static Builder builder() {
        return new Builder();
    }

    /* ---------- Builder ---------- */
    public static class Builder {
        /* Bắt buộc */
        private House house;
        private User  user;

        /* Tuỳ chọn – đặt mặc định an toàn */
        private HouseMemberRole role      = HouseMemberRole.MEMBER;
        private User invitedBy;
        private LocalDateTime joinedAt   = LocalDateTime.now();
        private LocalDateTime updatedAt   = LocalDateTime.now();

        /* Các setter dạng chain‑call */
        public Builder house(House house) {
            this.house = house;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder role(HouseMemberRole role) {
            if (role != null) this.role = role;
            return this;
        }

        public Builder invitedBy(User inviter) {
            this.invitedBy = inviter;
            return this;
        }

        public Builder joinedAt(LocalDateTime joinedAt) {
            if (joinedAt != null) this.joinedAt = joinedAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            if (updatedAt != null) this.updatedAt = updatedAt;
            return this;
        }

        /** Kiểm tra ràng buộc tối thiểu và tạo đối tượng. */
        public HouseMember build() {
            Objects.requireNonNull(house, "house is required");
            Objects.requireNonNull(user,  "user is required");
            return new HouseMember(this);
        }
    }
}
